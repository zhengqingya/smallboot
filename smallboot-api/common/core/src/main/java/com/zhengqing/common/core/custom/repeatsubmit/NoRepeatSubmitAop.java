package com.zhengqing.common.core.custom.repeatsubmit;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.zhengqing.common.base.constant.BaseConstant;
import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.common.web.util.IpUtil;
import com.zhengqing.common.web.util.ServletUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> aop切面 校验表单重复提交 </p>
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
        HttpServletRequest request = ServletUtil.getRequest();

        // 拿到ip地址、请求路径
        String ip = IpUtil.getIpAdrress(request);
        String restfulApi = request.getMethod() + ":" + request.getRequestURI();

        // 自定义key值
        String key = StrUtil.format(BaseConstant.BASE_PREFIX + ":no_repeat_submit:{}:{}:ip_{}", JwtUserContext.getUserId(), restfulApi, ip);

        // key存在的话说明频繁操作了...
        Assert.isFalse(RedisUtil.hasKey(key), noRepeatSubmit.msg());

        // 设置缓存key & 过期时间
        RedisUtil.setEx(key, restfulApi, noRepeatSubmit.time(), noRepeatSubmit.timeUnit());

        return pjp.proceed();
    }

}
