package com.zhengqing.common.redis.constant;

import com.zhengqing.common.base.constant.BaseConstant;

/**
 * <p>
 * 全局常用变量 - Redis缓存
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/10/12 14:47
 */
public interface RedisConstant {

    /**
     * ID生成
     */
    String ID_GENERATE_KEY_PREFIX = BaseConstant.BASE_PREFIX + ":id_generate:";
    /**
     * 记录重复ID
     */
    String ID_GENERATE_REPEAT_KEY = BaseConstant.BASE_PREFIX + ":id_generate_repeat";

    /**
     * 随机code码生成
     */
    String GENERATE_RANDOM_CODE_KEY = BaseConstant.BASE_PREFIX + ":generate-random-code";
    /**
     * 随机code码生成尝试次数记录 -- 用于码用尽告警
     */
    String GENERATE_RANDOM_CODE_RETRY_NUM_KEY = BaseConstant.BASE_PREFIX + ":generate-random-code:retry-num";
    int GENERATE_RANDOM_CODE_MAX_RETRY_NUM = 5;

    /**
     * 发布订阅通道
     */
    String REDIS_CHANNEL_TEST = "channel_test";
    
    /**
     * 缓存null值过期时间
     */
    Long CACHE_NULL_TTL = 2L;

}
