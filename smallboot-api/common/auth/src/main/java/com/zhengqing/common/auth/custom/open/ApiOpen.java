package com.zhengqing.common.auth.custom.open;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p> 自定义注解-开放url </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/8 9:31
 */
// 作用到方法上
@Target(ElementType.METHOD)
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiOpen {


}
