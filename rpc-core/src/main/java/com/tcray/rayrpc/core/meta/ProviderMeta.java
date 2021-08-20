package com.tcray.rayrpc.core.meta;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author lirui
 * provider 元信息
 */
@Data
public class ProviderMeta {

    /**
     * 实现类
     */
    private Object serviceImpl;

    /**
     * 方法
     */
    private Method method;

    /**
     * 方法签名
     */
    private String methodSign;

}
