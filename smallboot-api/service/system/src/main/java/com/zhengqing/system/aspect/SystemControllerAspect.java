package com.zhengqing.system.aspect;

import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.system.service.ISysMerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Controller 切面
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/9 17:47
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SystemControllerAspect {

    private final ISysMerchantService iSysMerchantService;

    @Pointcut("execution(* com.zhengqing.*..*.*Controller.*(..))")
    public void controllerPointCut() {
    }

    @Before("controllerPointCut()")
    public void controllerPointCut(JoinPoint joinPoint) {
        Integer merchantId = JwtUserContext.get().getMerchantId();
        if (merchantId == null) {
            return;
        }
        this.iSysMerchantService.checkData(merchantId);
    }


}
