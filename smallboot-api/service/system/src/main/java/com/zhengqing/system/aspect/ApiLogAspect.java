package com.zhengqing.system.aspect;

import cn.hutool.json.JSONUtil;
import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.common.web.util.ServletUtil;
import com.zhengqing.system.enums.SysLogTypeEnum;
import com.zhengqing.system.model.dto.SysLogSaveDTO;
import com.zhengqing.system.service.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
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
@RequiredArgsConstructor
public class ApiLogAspect {

    @Value("${spring.profiles.active:dev}")
    private String env;
    private final ISysLogService iSysLogService;

    /**
     * 配置织入点
     */
    @Pointcut("execution(* com.zhengqing.*..*.*Controller.*(..))")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        HttpServletRequest request = ServletUtil.getRequest();
        String userId = JwtUserContext.getUserId();
        String username = JwtUserContext.getUsername();

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 获取切入点所在的方法
        Api api = signature.getMethod().getDeclaringClass().getAnnotation(Api.class);
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

        String requestMethod = request.getMethod();
        String url = requestMethod + ":" + request.getRequestURI();
        log.debug("开始请求[{}] 操作人:[{}] 请求参数:{}", url, username, this.getRequestValue(request));

        // 处理业务
        Object result = joinPoint.proceed();

        Long time = System.currentTimeMillis() - start;
        log.debug("结束[{}] 耗时为:{}毫秒", url, time);

        if (HttpMethod.GET.name().equals(requestMethod)) {
            return result;
        }

        this.iSysLogService.addOrUpdateData(
                SysLogSaveDTO.builder()
                        .type(SysLogTypeEnum.操作日志.getType())
                        .apiMethod(signature.getDeclaringTypeName() + "." + signature.getName())
                        .apiMethodName(api.tags()[0] + "@" + apiOperation.value())
                        .apiHeader("")
                        .operationName(username)
                        .requestIp(request.getRemoteAddr())
                        .requestUrl(request.getRequestURI())
                        .requestHttpMethod(requestMethod)
                        .requestParams(this.getRequestValue(request))
                        .env(this.env)
                        .time(time.intValue())
                        .build()
        );

        return result;
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
