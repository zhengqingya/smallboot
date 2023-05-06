package com.zhengqing.app.api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.zhengqing.app.mapper.DemoMapper;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.limit.ApiLimit;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.common.redis.util.RedissonUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
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
@RequestMapping("/api/test/seckill")
@Api(tags = "test-seckill")
public class TestSeckillController extends BaseController {

    private final DemoMapper demoMapper;

    @SneakyThrows
    @GetMapping("00") // http://127.0.0.1:888/api/test/seckill/00
    @Transactional(rollbackFor = Exception.class)
    public void init() {
        RedisUtil.set("test", DateUtil.now());
    }

    @SneakyThrows
    @GetMapping("01") // http://127.0.0.1:888/api/test/seckill/01
    @ApiLimit(key = "'test'", rate = 5000, rateInterval = 1, msg = "操作频繁!（1秒内最多只能并发请求5000）")
    @Transactional(rollbackFor = Exception.class)
    public void db() {
//        TimeUnit.SECONDS.sleep(1);
        this.seckill();
    }

    @SneakyThrows
    @GetMapping("02") // http://127.0.0.1:888/api/test/seckill/02
    @Transactional(rollbackFor = Exception.class)
    public void redis() {
        RLock lock = RedissonUtil.getLock("seckill-lock");
        lock.lock();
        try {
            this.seckill();
        } finally {
            lock.unlock();
        }
    }

    private void seckill() {
        long updateNum = this.demoMapper.updateNum(1, -1);
        Assert.isTrue(updateNum > 0, "库存不足！");
    }

}
