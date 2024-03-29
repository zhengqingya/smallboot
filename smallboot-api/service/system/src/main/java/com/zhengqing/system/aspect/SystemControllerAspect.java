package com.zhengqing.system.aspect;

import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.model.bo.JwtUserBO;
import com.zhengqing.system.service.ISysTenantService;
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

    private final ISysTenantService iSysTenantService;

    @Pointcut("execution(* com.zhengqing.*..*.*Controller.*(..))")
    public void controllerPointCut() {
    }

    @Before("controllerPointCut()")
    public void controllerPointCut(JoinPoint joinPoint) {
        JwtUserBO jwtUserBO = JwtUserContext.get();
        if (jwtUserBO == null) {
            return;
        }
        Integer deptId = jwtUserBO.getDeptId();
        if (deptId == null) {
            return;
        }
        Integer tenantId = TenantIdContext.getTenantId();
        if (AppConstant.SMALL_BOOT_TENANT_ID.equals(tenantId)) {
            return;
        }
        this.iSysTenantService.checkData(tenantId);
    }


}
