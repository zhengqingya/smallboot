package com.zhengqing.common.core.custom.fileprefix;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

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
            this.handleField(result);
        } catch (Exception e) {
            log.error("[FilePrefixAop] 处理异常：", e);
            return result;
        }
        return result;
    }

    @SneakyThrows(Exception.class)
    private void handleField(Object data) {
        if (data instanceof List) {
            ((List<?>) data).forEach(this::handleField);
            return;
        }

        // 看下类上是否存在处理注解
        FilePrefixValid filePrefixValid = AnnotationUtils.findAnnotation(data.getClass(), FilePrefixValid.class);
        if (filePrefixValid == null) {
            return;
        }

        // 再看看字段
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(FilePrefix.class)) {
                this.handleField(data, field);
            } else if (field.isAnnotationPresent(FilePrefixValid.class)) {
                Object fieldValue = field.get(data);
                if (fieldValue instanceof List) {
                    this.handleField(fieldValue);
                } else {
                    throw new Exception("[FilePrefixValid] 暂未定义处理该数据类型！");
                }
            }
        }
    }

    @SneakyThrows(Exception.class)
    private void handleField(Object data, Field field) {
        field.setAccessible(true);
        Object value = field.get(data);
        if (value instanceof String) {
            String urlValue = (String) value;
            if (HttpUtil.isHttp(urlValue) || HttpUtil.isHttps(urlValue)) {
                // http/https 开头的替换前缀
                urlValue = URLUtil.getPath(urlValue);
            }
            // 填充url前缀地址值
            field.set(data, this.baseFileUrl + urlValue);
        }
    }

}
