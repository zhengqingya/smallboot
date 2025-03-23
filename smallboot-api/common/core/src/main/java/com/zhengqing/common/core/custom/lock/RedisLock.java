package com.zhengqing.common.core.custom.lock;

import com.zhengqing.common.base.constant.BaseConstant;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * <p> 自定义注解-基于redisson的分布式锁 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/1/18 14:22
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisLock {

    /**
     * 锁的资源，key。支持spring El表达式
     */
//    @AliasFor("key")
    String key() default "'" + BaseConstant.BASE_PREFIX + "':redis_lock'";

    /**
     * 锁类型
     */
    RedisLockType lockType() default RedisLockType.REENTRANT_LOCK;

    /**
     * 获取锁等待时间，默认3秒
     */
    long waitTime() default 3000L;

    /**
     * 锁自动释放时间，默认30秒
     */
    long leaseTime() default 30000L;

    /**
     * 时间单位，默认毫秒（获取锁等待时间和持锁时间都用此单位）
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

}
