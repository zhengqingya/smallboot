package com.zhengqing.common.core.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.BaseConstant;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.redis.constant.RedisConstant;
import com.zhengqing.common.redis.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
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
@DependsOn({"redisUtil"})
public class IdGeneratorUtil {

    /**
     * 终端ID
     */
    private static long workerId = 0;
    /**
     * 数据中心ID
     */
    private static long datacenterId = 1;

    private static Snowflake snowflake;

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 缓存key
     */
    private static String ID_GENERATE = BaseConstant.BASE_PREFIX + ":id_generate:";

    @PostConstruct
    public void init() {
        try {
//            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()); // hutool有限制范围在0L-31L之间，超出会报异常
            // 将所有可选的 workerId 和 datacenterId 放入redis中，每次启动服务时取，服务停止时再返回
            ID_GENERATE += this.applicationName;
            this.setSnow();
            SnowBO snowData = this.getSnow();
            workerId = snowData.getWorkerId();
            datacenterId = snowData.getDatacenterId();
            log.info("[{}] 当前机器的IP:[{}], workerId:[{}] datacenterId:[{}]", this.applicationName, NetUtil.getLocalhostStr(), workerId, datacenterId);
            snowflake = IdUtil.getSnowflake(workerId, datacenterId);
        } catch (Exception e) {
//            workerId = NetUtil.getLocalhostStr().hashCode();
            throw e;
        }
    }

    /**
     * 生成WorkerId和DatacenterId池
     * 目前有1024组
     *
     * @return void
     * @author zhengqingya
     * @date 2023/5/11 18:18
     */
    private void setSnow() {
        if (RedisUtil.hasKey(ID_GENERATE)) {
            return;
        }
        List<String> list = Lists.newArrayList();
        for (long i = 0; i <= 31; i++) {
            for (long j = 0; j <= 31; j++) {
                list.add(JSONUtil.toJsonStr(SnowBO.builder().workerId(i).datacenterId(j).build()));
            }
        }
        RedisUtil.lRightPushAll(ID_GENERATE, list);
    }

    /**
     * 取一组WorkerId和DatacenterId
     *
     * @return void
     * @author zhengqingya
     * @date 2023/5/11 18:18
     */
    private SnowBO getSnow() {
        String snowStr = RedisUtil.lLeftPop(ID_GENERATE);
        Assert.notBlank(snowStr, this.applicationName + "：Snow池暂无数据可取");
        return JSONUtil.toBean(snowStr, SnowBO.class);
    }

    /**
     * 返回一组WorkerId和DatacenterId
     *
     * @return void
     * @author zhengqingya
     * @date 2023/5/11 18:18
     */
    public static void backSnowData() {
        RedisUtil.lLeftPush(ID_GENERATE, JSONUtil.toJsonStr(SnowBO.builder().workerId(workerId).datacenterId(datacenterId).build()));
        log.info("[雪花ID] 服务销毁时归还 workerId:[{}] datacenterId:[{}]", workerId, datacenterId);
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
            log.info("ID: {}", IdUtil.getSnowflake(31, 31).nextId());
        }
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SnowBO {
        /**
         * 终端ID
         */
        private Long workerId;
        /**
         * 数据中心ID
         */
        private Long datacenterId;
    }

}
