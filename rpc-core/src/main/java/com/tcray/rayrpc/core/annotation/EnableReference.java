package com.tcray.rayrpc.core.annotation;

import java.lang.annotation.*;

/**
 * @author lirui
 * 使用远程调用
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface EnableReference {

}