package com.zhengqing.app.api;

import cn.hutool.core.date.DateUtil;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.redis.util.RedissonUtil;
import com.zhengqing.system.mapper.SysUserMapper;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * <p> 测试接口  </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test/redisson")
@Api(tags = "test-api")
public class TestRedissonController extends BaseController {

    private final SysUserMapper sysUserMapper;
    private final JdbcTemplate jdbcTemplate;

    @SneakyThrows
    @GetMapping("lock01") // http://127.0.0.1:888/api/test/redisson/lock01
    public void lock01() {
        RLock lock = RedissonUtil.lock("test-lock", 3, TimeUnit.SECONDS);
        try {
            log.info(DateUtil.now());
            this.jdbcTemplate.execute("insert into t_demo (username, password) values ('test','123456')");
//            TimeUnit.SECONDS.sleep(5);
        } finally {
            lock.unlock();
        }
    }

}
