package com.zhengqing.common.core.custom.limit;

import cn.hutool.core.lang.Assert;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.SpringElUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * <p> aop切面-redis限流处理 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/8 9:36
 */
@Slf4j
@Aspect
@Component
public class ApiLimitAspect {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 【环绕通知】 用于拦截指定方法，判断用户表单保存操作是否属于重复提交
     * 定义切入点表达式： execution(public * (…)) 表达式解释： execution：主体 public:可省略 *：标识方法的任意返回值 任意包+类+方法(…) 任意参数
     * com.zhengqing.mall.controller.*Controller.*(..)) ： 标识AOP所切服务的包名，即需要进行横切的业务类 .*Controller ： 标识类名，*即所有类 .*(..) ：
     * 标识任何方法名，括号表示参数，两个点表示任何参数类型
     *
     * @param pjp      切入点对象
     * @param apiLimit 自定义的注解对象
     * @return java.lang.Object
     */
    @SneakyThrows
    @Around("( execution(* com.zhengqing.*..*.*Controller.*(..))" +
            " || execution(* com.zhengqing.*.feign.*(..)) ) && @annotation(apiLimit)")
    public Object doAround(ProceedingJoinPoint pjp, ApiLimit apiLimit) {
        // 数据获取
        String key = apiLimit.key();
        Assert.notBlank(key, "限流器key不能为空！");

        // 支持spring El表达式
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();
        key = SpringElUtil.parse(key, method, args);

        RateType rateType = apiLimit.rateType();
        long rate = apiLimit.rate();
        long rateInterval = apiLimit.rateInterval();
        RateIntervalUnit rateIntervalUnit = apiLimit.rateIntervalUnit();
        String msg = apiLimit.msg();
        Assert.isTrue(
                RateIntervalUnit.MILLISECONDS == rateIntervalUnit
                        || RateIntervalUnit.SECONDS == rateIntervalUnit, "限流器速率间隔时间单位最多只能为秒！"
        );

        // 1、声明一个限流器
        RRateLimiter rRateLimiter = this.redissonClient.getRateLimiter(key);
        // 2、设置速率，[rateInterval]秒中产生[rate]个令牌
        rRateLimiter.trySetRate(rateType, rate, rateInterval, rateIntervalUnit);
        // 这个时间设置太长的话，测试时需要手动去redis中删除已存在的key，很不方便策略改动...
        rRateLimiter.expire(1, TimeUnit.MINUTES);
        // 3、试图获取一个令牌，获取到返回true
        if (rRateLimiter.tryAcquire()) {
            return pjp.proceed();
        } else {
            throw new MyException(msg);
        }
    }

}
