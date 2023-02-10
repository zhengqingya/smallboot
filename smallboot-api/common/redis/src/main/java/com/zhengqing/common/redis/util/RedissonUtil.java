package com.zhengqing.common.redis.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Redisson工具类
 * </p>
 *
 * @author zhengqingya
 * @description 文档见：
 * - https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95
 * - https://www.bookstack.cn/read/redisson-wiki-zh/spilt.1.8.-%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E5%92%8C%E5%90%8C%E6%AD%A5%E5%99%A8.md
 * @date 2019/11/27 14:38
 */
@Component
public class RedissonUtil {

    private static RedissonClient redissonClient;

    @Autowired
    public RedissonUtil(RedissonClient redissonClient) {
        RedissonUtil.redissonClient = redissonClient;
    }


    // ============================ ↓↓↓↓↓↓ 操作 锁 ↓↓↓↓↓↓ ============================

    /**
     * 可重入锁
     *
     * @param key key
     * @return 锁
     */
    public static RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    /**
     * 可重入锁
     *
     * @param key       key
     * @param leaseTime 时间
     * @param unit      时间单位
     * @return 锁
     * @author zhengqingya
     * @date 2022/1/14 9:25 下午
     */
    public static RLock lock(String key, long leaseTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(key);
        // 加锁leaseTime以后自动解锁
        // 无需调用unlock方法手动解锁
        lock.lock(leaseTime, unit);
        return lock;
    }

}
