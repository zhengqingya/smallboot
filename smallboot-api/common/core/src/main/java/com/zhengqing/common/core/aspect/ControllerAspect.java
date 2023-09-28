package com.zhengqing.common.core.aspect;

import com.zhengqing.common.base.constant.BaseConstant;
import com.zhengqing.common.base.context.SysUserContext;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.base.model.dto.BasePageDTO;
import com.zhengqing.common.core.custom.parameter.CheckParam;
import com.zhengqing.common.core.custom.parameter.HandleParam;
import com.zhengqing.common.web.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

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
public class ControllerAspect {

    /**
     * 配置织入点
     */
    @Pointcut("execution(* com.zhengqing.*..*.*Controller.*(..))")
    public void controllerPointCut() {
    }

    /**
     * Before增强：在目标方法被执行的时候织入增强
     * <p>
     * Controller所有方法为切入点
     */
    @Before("controllerPointCut()")
    public void controllerPointCut(JoinPoint joinPoint) {
        // 参数处理
        Object[] paramObjArray = joinPoint.getArgs();
        // 遍历所有传入参数,赋值
        for (Object paramObj : paramObjArray) {
            // dto参数处理
            if (paramObj instanceof BaseDTO) {
                BaseDTO baseDTO = (BaseDTO) paramObj;
                baseDTO.setCurrentUserId(SysUserContext.getUserId());
                baseDTO.setCurrentUsername(SysUserContext.getUsername());
            }

            // page参数处理
            if (paramObj instanceof BasePageDTO) {
                BasePageDTO pageDTO = (BasePageDTO) paramObj;
                pageDTO.setPageNum(ServletUtil.getParameterToInt(BaseConstant.PAGE_NUM, BaseConstant.DEFAULT_PAGE_NUM));
                pageDTO.setPageSize(ServletUtil.getParameterToInt(BaseConstant.PAGE_SIZE, BaseConstant.DEFAULT_PAGE_SIZE));
            }


            this.handleData(paramObj);

            if (paramObj instanceof List) {
                List list = (List) paramObj;
                list.forEach(this::handleData);
            }
        }
    }

    private void handleData(Object paramObj) {
        // 参数校验处理
        if (paramObj instanceof CheckParam) {
            CheckParam checkParam = (CheckParam) paramObj;
            checkParam.checkParam();
        }

        // 处理参数数据
        if (paramObj instanceof HandleParam) {
            HandleParam handleParam = (HandleParam) paramObj;
            handleParam.handleParam();
        }
    }

}
