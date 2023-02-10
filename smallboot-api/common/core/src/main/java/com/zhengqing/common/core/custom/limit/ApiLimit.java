package com.zhengqing.common.core.custom.limit;

import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p> 自定义注解-基于redis的限流 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/8 9:31
 */
// 作用到方法上
@Target(ElementType.METHOD)
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLimit {

    /**
     * 限流器key
     */
    String key();

    /**
     * 速率类型，默认所有客户端加总限流
     */
    RateType rateType() default RateType.OVERALL;

    /**
     * 速率数，默认1
     */
    long rate() default 1;

    /**
     * 速率间隔时间，默认3秒
     */
    long rateInterval() default 3;

    /**
     * 速率间隔时间单位，默认秒
     */
    RateIntervalUnit rateIntervalUnit() default RateIntervalUnit.SECONDS;

    /**
     * 默认限流提示信息
     */
    String msg() default "操作频繁,请稍后再试!";

}
