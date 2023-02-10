package com.zhengqing.common.core.custom.lock;

import com.zhengqing.common.base.util.SpringElUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * <p> aop切面-redisson分布式锁 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/1/18 14:24
 */
@Slf4j
@Aspect
@Component
@ConditionalOnBean(RedissonClient.class)
@AutoConfigureAfter(RedissonAutoConfiguration.class)
public class RedisLockAspect {

    @Resource
    private RedissonClient redissonClient;

    @Around("@annotation(com.zhengqing.common.core.custom.lock.RedisLock)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String key = redisLock.key();
        Object[] args = pjp.getArgs();
        // 支持spring El表达式
        key = SpringElUtil.parse(key, method, args);
        // 获取锁
        RLock lock = this.getLock(key, redisLock);
        lock.lock(redisLock.leaseTime(), redisLock.unit());
        try {
            return pjp.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            // 释放锁
            lock.unlock();
        }
    }


    /**
     * 获取锁
     *
     * @param key       key
     * @param redisLock 分布式锁注解
     * @return 锁
     * @author zhengqingya
     * @date 2022/1/18 14:28
     */
    private RLock getLock(String key, RedisLock redisLock) {
        switch (redisLock.lockType()) {
            case REENTRANT_LOCK:
                return this.redissonClient.getLock(key);
            case FAIR_LOCK:
                return this.redissonClient.getFairLock(key);
            case READ_LOCK:
                return this.redissonClient.getReadWriteLock(key).readLock();
            case WRITE_LOCK:
                return this.redissonClient.getReadWriteLock(key).writeLock();
            default:
                throw new RuntimeException("do not support lock type:" + redisLock.lockType().name());
        }
    }

}
