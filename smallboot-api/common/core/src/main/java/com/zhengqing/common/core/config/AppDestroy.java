package com.zhengqing.common.core.config;

import cn.hutool.core.date.DateUtil;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * <p> 服务销毁停止时触发 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/18 12:36
 */
@Slf4j
@Component
public class AppDestroy {
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 这里注入下，防止该bean提前销毁，导致下面的逻辑处理异常
     */
    @Resource
    private RedisUtil redisUtil;

    @PreDestroy
    public void preDestroy() {
        log.info("[{}]服务停止：{}", this.applicationName, DateUtil.now());
        
        // 归还雪花ID策略池数据
        IdGeneratorUtil.backSnowData();
    }

}
