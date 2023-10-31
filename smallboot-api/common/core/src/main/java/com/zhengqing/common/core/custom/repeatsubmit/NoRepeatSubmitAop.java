package com.zhengqing.common.core.custom.repeatsubmit;

import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.common.web.util.IpUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>
 * aop切面 校验表单重复提交
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/11/27 11:26
 */
@Slf4j
@Aspect
@Component
public class NoRepeatSubmitAop {

    /**
     * 【环绕通知】 用于拦截指定方法，判断用户表单保存操作是否属于重复提交
     * 定义切入点表达式： execution(public * (…)) 表达式解释：
     * -                          execution => 主体
     * -                          public => 可省略
     * -                          * => 标识方法的任意返回值 任意包+类+方法(…) 任意参数
     * -                 com.zhengqing.*.*.api => 标识AOP所切服务的包名，即需要进行横切的业务类
     * -                          .*Controller => 标识类名，*即所有类
     * -                          .*(..) => 标识任何方法名，括号表示参数，两个点表示任何参数类型
     *
     * @param pjp            切入点对象
     * @param noRepeatSubmit 自定义的注解对象
     * @return java.lang.Object
     */
    @SneakyThrows
    @Around("( execution(* com.zhengqing.*.api.*Controller.*(..)) || execution(* com.zhengqing..*.api.*Controller.*(..)) || execution(* com.zhengqing..*.api.*.*Controller.*(..)) ) && @annotation(noRepeatSubmit)")
    public Object doAround(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) {
        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();

        // 拿到ip地址、请求路径
        String ip = IpUtil.getIpAdrress(request);
        String url = request.getRequestURL().toString();

        // 现在时间
        long now = System.currentTimeMillis();

        // 自定义key值方式
        String key = "REQUEST_FORM_" + ip;
        if (RedisUtil.hasKey(key)) {
            // 上次表单提交时间
            long lastTime = Long.parseLong(RedisUtil.get(key));
            // 如果现在距离上次提交时间小于设置的默认时间 则 判断为重复提交 否则 正常提交 -> 进入业务处理
            if ((now - lastTime) > noRepeatSubmit.time()) {
                // 非重复提交操作 - 重新记录操作时间
                RedisUtil.set(key, String.valueOf(now));
                // 进入处理业务
                // ApiResult result = (ApiResult)pjp.proceed();
                return pjp.proceed();
            } else {
                throw new MyException("请勿频繁操作!");
            }
        } else {
            // 这里是第一次操作
            RedisUtil.set(key, String.valueOf(now));
            // ApiResult result = (ApiResult)pjp.proceed();
            return pjp.proceed();
        }

    }

}
