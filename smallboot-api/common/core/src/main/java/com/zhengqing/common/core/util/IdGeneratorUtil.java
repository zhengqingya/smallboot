package com.zhengqing.common.core.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.redis.constant.RedisConstant;
import com.zhengqing.common.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;


/**
 * <p> Hutool之雪花算法生成唯一ID配置 </p>
 *
 * @author zhengqingya
 * @description 可参考 https://www.bookstack.cn/read/hutool/bfd2d43bcada297e.md
 * @date 2021/10/29 16:57
 */
@Slf4j
@Component
public class IdGeneratorUtil {

    /**
     * 终端ID
     */
    private static long workerId = 0;
    /**
     * 数据中心ID
     */
    private static long datacenterId = 1;

    private static Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);

    @PostConstruct
    public void init() {
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("当前机器的IP:[{}], workerId:[{}]", NetUtil.getLocalhostStr(), workerId);
        } catch (Exception e) {
            log.error("获取当前机器workerId 异常", e);
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
    }

    /**
     * 使用默认的 workerId 和 datacenterId
     */
    public synchronized static long snowflakeId() {
        return snowflake.nextId();
    }

    /**
     * 字符串类型
     */
    public static String nextStrId() {
        return String.valueOf(snowflakeId());
    }

    /**
     * 使用自定义的 workerId 和 datacenterId
     */
    public synchronized static long snowflakeId(long workerId, long datacenterId) {
        return IdUtil.getSnowflake(workerId, datacenterId).nextId();
    }

    /**
     * 使用redis确保分布式系统主键ID唯一性
     *
     * @return ID
     * @author zhengqingya
     * @date 2022/7/4 12:47
     */
    public synchronized static long nextId() {
        long id = snowflake.nextId();
        String key = RedisConstant.ID_GENERATE_KEY_PREFIX + id;
        if (!RedisUtil.setIfAbsent(key, String.valueOf(id))) {
            // 记录下重复数据
            RedisUtil.hPutIfAbsent(RedisConstant.ID_GENERATE_REPEAT_KEY, String.valueOf(id), MyDateUtil.nowStr());
            // 循环继续获取
            return nextId();
        }
        // 设置3分钟过期
        RedisUtil.expire(key, 3, TimeUnit.MINUTES);
        return id;
    }

    /**
     * 随机码生成 -- 12位英文大写字母+数字
     *
     * @return 随机码
     * @author zhengqingya
     * @date 2022/4/20 16:48
     */
    public static String generateRandomCode() {
        String key = RedisConstant.GENERATE_RANDOM_CODE_KEY;
        String code = RandomUtil.randomStringUpper(12);
        if (!RedisUtil.hPutIfAbsent(key, code, code)) {
            // 如果重试次数超过5次则告警...
            Long retryNum = RedisUtil.incrBy(RedisConstant.GENERATE_RANDOM_CODE_RETRY_NUM_KEY, 1);
            if (retryNum > RedisConstant.GENERATE_RANDOM_CODE_MAX_RETRY_NUM) {
                // 先删除key，防止下次进来直接异常退出程序
                RedisUtil.delete(RedisConstant.GENERATE_RANDOM_CODE_RETRY_NUM_KEY);
                throw new MyException("随机码已用尽，请联系系统管理员！");
            }

            // 如果存在了，继续拿数据
            return generateRandomCode();
        }
        // 正常拿到数据返回
        return code;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
//            log.info("ID: {}", IdGeneratorUtil.snowflakeId(i % 2, i % 2));
            log.info("ID: {}", IdGeneratorUtil.snowflakeId());
        }
    }

}
