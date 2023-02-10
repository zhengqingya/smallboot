package com.zhengqing.common.core.aspect;

import cn.hutool.json.JSONUtil;
import com.zhengqing.common.base.context.SysUserContext;
import com.zhengqing.common.web.util.ServletUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 日志切面
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/8/1 19:09
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(
        value = {"smallboot.api-log"},
        havingValue = "true",
        // true表示缺少此配置属性时也会加载该bean
        matchIfMissing = true
)
public class ApiLogAspect {

    /**
     * 配置织入点
     */
    @Pointcut("execution(* com.zhengqing.*..*.*Controller.*(..))")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doAround(JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ServletUtil.getRequest();
        if (request != null) {
            Integer userId = SysUserContext.getUserId();
            String username = SysUserContext.getUsername();

            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            ApiOperation apiOperation = signature.getMethod().getAnnotation(ApiOperation.class);

//            log.debug("========================== ↓↓↓↓↓↓ 《ApiLogAspect》 Start... ↓↓↓↓↓↓ ==========================");
//            log.debug("《ApiLogAspect》 controller method: {}",
//                    signature.getDeclaringTypeName() + "." + signature.getName());
//            log.debug("《ApiLogAspect》 controller method description: {}", apiOperation.value());
//            log.debug("《ApiLogAspect》 operatorId: {}", userId);
//            log.debug("《ApiLogAspect》 operatorName: {}", username);
//            log.debug("《ApiLogAspect》 request header: {}", request.getHeader(SwaggerConstant.REQUEST_HEADER_AUTHORIZATION));
//            log.debug("《ApiLogAspect》 request ip: {}", request.getRemoteAddr());
//            log.debug("《ApiLogAspect》 request url: {}", request.getRequestURL().toString());
//            log.debug("《ApiLogAspect》 request http method: {}", request.getMethod());
//            log.debug("《ApiLogAspect》 request params: {}", this.getRequestValue(request));
//            log.debug("========================== ↑↑↑↑↑↑ 《ApiLogAspect》 End... ↑↑↑↑↑↑ ==========================");

            log.debug("[{}] [{}] 请求参数：{}",
                    request.getMethod(),
                    signature.getDeclaringTypeName() + "." + signature.getName(),
                    this.getRequestValue(request)
            );
        }
    }

    /**
     * 获取请求的参数
     */
    private String getRequestValue(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        String params = JSONUtil.toJsonStr(map);
        return StringUtils.substring(params, 0, 2000);
    }

}
