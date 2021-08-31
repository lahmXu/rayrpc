package com.tcray.rayrpc.core.meta;

import com.tcray.rayrpc.core.handler.RpcInvocationHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @author lirui
 * rpc context 存储 rpc 相关的信息
 * 发送端上下文
 */
public class RpcContext {

    /**
     * 存储 provider 信息
     * 使用 MultiValueMap 存储,表示一个类有多个方法
     */
    @Getter
    private MultiValueMap<String, ProviderMeta> providerHolder = new LinkedMultiValueMap<>();

    @Getter
    @Setter
    private ExecutorService executePool;

    @Getter
    @Setter
    private Map<Long, CallResultFuture> requestPool = new ConcurrentHashMap<>();

    @Getter
    @Setter
    private Map<String, RpcInvocationHandler> ConsumerHolder = new HashMap<>();

}
