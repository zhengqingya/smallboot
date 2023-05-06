package com.zhengqing.app.api;

import cn.hutool.core.date.DateUtil;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.redis.util.RedissonUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "test-redisson")
public class TestRedissonController extends BaseController {

    private final JdbcTemplate jdbcTemplate;

    @SneakyThrows
    @GetMapping("lock01") // http://127.0.0.1:888/api/test/redisson/lock01
    @Transactional(rollbackFor = Exception.class)
    public void lock01() {
        String threadName = Thread.currentThread().getName();
        System.err.println(String.format("[%s] 接口请求时间：%s", threadName, DateUtil.now()));

        // 等5秒锁释放才能执行后面的逻辑
//        RLock lock = RedissonUtil.lock("test-lock", 5, TimeUnit.SECONDS);
        // 最多等待1秒
//        RLock lock = RedissonUtil.tryLock("test-lock", 1, 5, TimeUnit.SECONDS);
        // 看门狗机制 -- 等待业务执行完后释放锁，下一个线程才能进来执行业务
        RLock lock = RedissonUtil.getLock("test-lock");
        lock.lock();
        System.err.println(String.format("[%s] 锁进入时间：%s", threadName, DateUtil.now()));
        try {
            System.err.println(threadName + ": 插入数据 " + DateUtil.now());
            this.jdbcTemplate.execute(String.format("insert into demo.t_demo (username, password) values ('%s','123456')", DateUtil.now()));
//            TimeUnit.SECONDS.sleep(5);
            int a = 1 / 0;
        } finally {
            // 如果锁时间设置太小，这里拿不到锁时去释放锁会报错：”java.lang.IllegalMonitorStateException: attempt to unlock lock, not locked by current thread by node id: b31ac953-3836-41ef-bf74-c0cb683df7a0 thread-id: 88“
            lock.unlock();
        }
    }

}
