package com.zhengqing.common.core.custom.fileprefix;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * <p> aop切面-填充文件地址 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/8 9:31
 */
@Slf4j
@Aspect
@Component
public class FilePrefixAop {

    @Value("${smallboot.base-file-url}")
    private String baseFileUrl;

    @Around("execution(* com.zhengqing.*.api.*Controller.*(..)) || execution(* com.zhengqing..*.api..*.*Controller.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        if (result == null) {
            return null;
        }
        try {
            Field[] fields = result.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(FilePrefix.class)) {
                    field.setAccessible(true);
                    Object value = field.get(result);
                    if (value instanceof String) {
                        String urlValue = (String) value;
                        if (HttpUtil.isHttp(urlValue) || HttpUtil.isHttps(urlValue)) {
                            continue;
                        }
                        field.set(result, this.baseFileUrl + urlValue);
                    }
                }
            }
        } catch (Exception e) {
            log.error("[FilePrefixAop] 处理异常：", e);
            return result;
        }
        return result;
    }

}
