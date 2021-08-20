package com.tcray.rayrpc.core.annotation;

import java.lang.annotation.*;

/**
 * @author lirui
 * 注册服务,提供api调用
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface EnableProvider {

}