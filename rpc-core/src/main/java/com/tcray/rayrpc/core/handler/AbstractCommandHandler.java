package com.tcray.rayrpc.core.handler;

import com.tcray.rayrpc.core.meta.ProviderMeta;
import com.tcray.rayrpc.core.meta.RpcContext;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author lirui
 * 抽象命令处理器
 * 抽取公共方法
 */

public abstract class AbstractCommandHandler implements CommandHandler {

    /**
     * 查询对应的 provider 元信息, 确认是否有对应服务注册进来
     * @param rpcContext
     * @param interfaceName
     * @param methodSign
     * @return
     */
    protected ProviderMeta findProvider(RpcContext rpcContext, String interfaceName, String methodSign) {
        List<ProviderMeta> providerMetas = rpcContext.getProviderHolder().get(interfaceName);
        if (!CollectionUtils.isEmpty(providerMetas)) {
            Optional<ProviderMeta> providerMeta = providerMetas.stream().filter(provider -> methodSign.equals(provider.getMethodSign())).findFirst();
            return providerMeta.orElse(null);
        }
        return null;
    }

}
