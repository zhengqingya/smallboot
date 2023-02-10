package com.zhengqing.common.core.custom.lock;

/**
 * <p> 基于redisson的分布式锁类型 </p>
 *
 * @author zhengqingya
 * @description https://www.bookstack.cn/read/redisson-wiki-zh/8.-%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E5%92%8C%E5%90%8C%E6%AD%A5%E5%99%A8.md
 * @date 2022/1/18 14:23
 */
public enum RedisLockType {

    /**
     * 可重入锁
     */
    REENTRANT_LOCK,

    /**
     * 公平锁
     */
    FAIR_LOCK,

    /**
     * 读锁
     */
    READ_LOCK,

    /**
     * 写锁
     */
    WRITE_LOCK;

}
