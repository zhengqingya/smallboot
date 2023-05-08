package com.zhengqing.app;

import ch.qos.logback.classic.Level;
import cn.hutool.core.date.DateUtil;
import com.zhengqing.common.log.util.LogLevelUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LogLevelTest {

    @Test
    public void test() throws Exception {
        LogLevelUtil.update("com.zhengqing", Level.INFO);
        log.trace("time: {}", DateUtil.now());
        log.debug("time: {}", DateUtil.now());
        log.info("time: {}", DateUtil.now());
        log.warn("time: {}", DateUtil.now());
        log.error("time: {}", DateUtil.now());
    }

}

