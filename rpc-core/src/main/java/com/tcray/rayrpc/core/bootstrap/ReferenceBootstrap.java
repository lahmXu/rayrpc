package com.tcray.rayrpc.core.bootstrap;

import com.tcray.rayrpc.core.annotation.EnableReference;
import com.tcray.rayrpc.core.connection.ConnectionGroup;
import com.tcray.rayrpc.core.meta.RpcContext;
import com.tcray.rayrpc.core.stub.StubSkeletonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lirui
 */
@Slf4j
@Component
@ConditionalOnBean(name = "rpcConsumerApplication")
public class ReferenceBootstrap implements Closeable, InstantiationAwareBeanPostProcessor {

    private ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(300);
    private ExecutorService threadPool = new ThreadPoolExecutor(4, 80, 60, TimeUnit.SECONDS, workQueue);

    private RpcContext rpcContext = new RpcContext();

    private ConnectionGroup connectionGroup = new ConnectionGroup();

    private Map<String, String> serverList;

    private String scanPackage = "com.tcray.rayrpc";

    @Override
    public void close() throws IOException {
        connectionGroup.close();
    }

    @PostConstruct
    public void start() {
        // 加上rejectHandle
        rpcContext.setExecutePool(threadPool);

        // 根据 server config 创建 server, 可以从数据库中读取,之后做负载
        // 这边只做单个配置
        // todo: use Configuration
        serverList = new HashMap<>();
        serverList.put("127.0.0.1", "6300");

        // 创建 consumer client, 供后面建立连接使用
        connectionGroup.startClient(serverList, rpcContext);
    }

    /**
     * 通过实现 InstantiationAwareBeanPostProcessor 来筛选出 @EnableReference 的类
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {

        // 筛选出指定包下面的类
        if (bean.getClass().getPackage().getName().startsWith(scanPackage)) {
            Field[] declaredFields = bean.getClass().getDeclaredFields();
            List<Field> consumers = Arrays.stream(declaredFields).filter(field -> field.isAnnotationPresent(EnableReference.class)).collect(Collectors.toList());

            // 将带有注解的成员变量设置为新创建的 consumer
            consumers.stream().forEach(consumer -> {
                Object consumer1 = createConsumer(consumer.getType());
                try {
                    consumer.setAccessible(true);
                    consumer.set(bean, consumer1);
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }
            });
        }
        return null;
    }

    private <T> T createConsumer(Class<?> clazz) {
        return StubSkeletonHelper.createConsumer(connectionGroup, clazz, rpcContext);
    }

}
