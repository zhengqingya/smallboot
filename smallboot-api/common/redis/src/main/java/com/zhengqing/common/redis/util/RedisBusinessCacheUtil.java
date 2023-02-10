package com.zhengqing.common.redis.util;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zhengqing.common.redis.constant.RedisConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * <p>
 * Redis业务缓存工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/11/27 14:38
 */
@Component
public class RedisBusinessCacheUtil {

    private static StringRedisTemplate redisTemplate;

    @Autowired
    public RedisBusinessCacheUtil(StringRedisTemplate redisTemplate) {
        RedisBusinessCacheUtil.redisTemplate = redisTemplate;
    }


    // ============================ ↓↓↓↓↓↓ 统一解决缓存问题 ↓↓↓↓↓↓ ============================

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    static class RedisData {
        private LocalDateTime expireTime;
        private Object data;
    }

    /**
     * 逻辑过期
     *
     * @param key   缓存key
     * @param value 值
     * @param time  时间
     * @param unit  时间单位
     * @return void
     * @author zhengqingya
     * @date 2022/9/30 13:50
     */
    public static void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        // 设置逻辑过期
        RedisData redisData = RedisData.builder()
                .data(value)
                // LocalDateTime.now() 获取当前时间
                // plusSeconds 添加秒数
                // 使用TimeUnit包的 toSeconds将时间转换为秒数
                .expireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)))
                .build();
        // 写入Redis
        RedisUtil.set(key, JSONUtil.toJsonStr(redisData));
    }

    /**
     * 利用缓存空值的方式解决缓存穿透
     *
     * @param key        缓存key
     * @param id         查db时的参数
     * @param type       返回结果类型
     * @param dbFallback ID:参数 R:返回值
     * @param time       时间
     * @param unit       时间单位
     * @return 返回值
     * @author zhengqingya
     * @date 2022/9/30 13:55
     */
    public static <R, ID> R queryWithPassThrough(String key, ID id, Class<R> type,
                                                 Function<ID, R> dbFallback,
                                                 Long time, TimeUnit unit) {
        // 1、先查缓存
        String json = RedisUtil.get(key);
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toBean(json, type);
        }
        // 判断命中的是否是空值“”
        if (json != null) {
            return null;
        }
        // 2、查库 -- 根据不同业务查询各自的数据库
        R r = dbFallback.apply(id);
        if (r == null) {
            // 将空值写入redis
            RedisUtil.setEx(key, "", RedisConstant.CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }
        // 3、结果写入缓存
        RedisUtil.setEx(key, r, time, unit);
        return r;
    }

    /**
     * 定义线程池
     */
    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    /**
     * 缓存击穿 -- 逻辑过期
     *
     * @param key        缓存key
     * @param id         查db时的参数
     * @param type       返回结果类型
     * @param dbFallback ID:参数 R:返回值
     * @param time       时间
     * @param unit       时间单位
     * @param lockKey    锁的key
     * @return 返回值
     * @author zhengqingya
     * @date 2022/9/30 14:15
     */
    public static <R, ID> R queryWithLogicalExpire(String key, ID id, Class<R> type,
                                                   Function<ID, R> dbFallback,
                                                   Long time, TimeUnit unit, String lockKey) {
        // 1、先查缓存
        String json = RedisUtil.get(key);
        if (StrUtil.isBlank(json)) {
            return null;
        }
        RedisData redisData = JSONUtil.toBean(json, RedisData.class);
        R r = JSONUtil.toBean((JSONObject) redisData.getData(), type);
        LocalDateTime expireTime = redisData.getExpireTime();
        // 2、判断是否过期
        if (expireTime.isAfter(LocalDateTime.now())) {
            // 2.1、未过期，直接返回
            return r;
        }
        // 2.2、已过期 -- 缓存重建
        // 获取互斥锁
        boolean isLock = tryLock(lockKey);
        if (isLock) {
            // 开启独立线程，实现缓存重建
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    // 查询数据库
                    R newR = dbFallback.apply(id);
                    // 重建缓存
                    setWithLogicalExpire(key, newR, time, unit);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    // 释放锁
                    unlock(lockKey);
                }
            });
        }
        // 返回过期的信息
        return r;
    }

    /**
     * 缓存击穿 -- 互斥锁
     *
     * @param key        缓存key
     * @param id         查db时的参数
     * @param type       返回结果类型
     * @param dbFallback ID:参数 R:返回值
     * @param time       时间
     * @param unit       时间单位
     * @param lockKey    锁的key
     * @return 返回值
     * @author zhengqingya
     * @date 2022/9/30 14:15
     */
    public static <R, ID> R queryWithMutex(String key, ID id, Class<R> type,
                                           Function<ID, R> dbFallback,
                                           Long time, TimeUnit unit, String lockKey) {
        // 1、先查缓存
        String json = RedisUtil.get(key);
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toBean(json, type);
        }
        if (json != null) {
            return null;
        }
        // 2、实现缓存重建
        // 获取互斥锁
        R r = null;
        try {
            boolean isLock = tryLock(lockKey);
            if (!isLock) {
                // 获取锁失败，休眠并重试
                Thread.sleep(50);
                return queryWithMutex(key, id, type, dbFallback, time, unit, lockKey);
            }
            // 获取锁成功，根据id查询数据库
            r = dbFallback.apply(id);
            if (r == null) {
                // 将空值写入redis
                RedisUtil.setEx(key, "", RedisConstant.CACHE_NULL_TTL, TimeUnit.MINUTES);
                return null;
            }
            // 存在则将数据写入redis
            RedisUtil.setEx(key, r, time, unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放锁
            unlock(lockKey);
        }
        // 3、返回
        return r;
    }

    /**
     * 获取互斥锁
     *
     * @param key 缓存key
     * @return true:成功  false:失败
     * @author zhengqingya
     * @date 2022/9/30 14:15
     */
    private static boolean tryLock(String key) {
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }

    /**
     * 释放锁
     *
     * @param key 缓存key
     * @author zhengqingya
     * @date 2022/9/30 14:15
     */
    private static void unlock(String key) {
        RedisUtil.delete(key);
    }

}
