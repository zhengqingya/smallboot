package com.zhengqing.common.core.custom.fileprefix;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * @description 说明
 * 1. {@link FilePrefixValid} 用于类上标注是否生效
 * 2. {@link FilePrefix} 用于填充字段url值
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
            this.handleClz(result);
        } catch (Exception e) {
            log.error("[FilePrefixAop] 处理异常：", e);
            return result;
        }
        return result;
    }

    @SneakyThrows(Exception.class)
    private void handleClz(Object data) {
        if (data instanceof List) {
            ((List<?>) data).forEach(this::handleClz);
            return;
        }

        // 解析分页中的数据
        Class<?> dataClz = data.getClass();
        if (data instanceof Page) {
            Page page = (Page) data;
            this.handleClz(page.getRecords());
            return;
        }

        // 看下类上是否存在处理注解
        FilePrefixValid filePrefixValid = AnnotationUtils.findAnnotation(dataClz, FilePrefixValid.class);
        if (filePrefixValid == null) {
            return;
        }

        // 再看看字段
        Field[] fields = dataClz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(FilePrefix.class)) {
                this.handleField(data, field);
            } else if (field.isAnnotationPresent(FilePrefixValid.class)) {
                Object fieldValue = field.get(data);
                if (fieldValue instanceof List) {
                    this.handleClz(fieldValue);
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
