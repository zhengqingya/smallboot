package com.zhengqing.common.auth.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.collect.Lists;
import com.zhengqing.common.auth.custom.open.ApiOpen;
import com.zhengqing.common.auth.util.AnnotationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

/**
 * <p> 注册 Sa-Token 路由拦截器 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/11/3 12:06
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SaTokenWebMvcConfig implements WebMvcConfigurer {

    private final SaTokenProperty saTokenProperty;
    private final AnnotationUtil annotationUtil;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> openUrlList = this.saTokenProperty.getOpenUrlList();
        openUrlList.addAll(this.getApiOpenList());

        // 注册1个登录认证拦截器 -- 参考： https://sa-token.cc/doc.html#/use/route-check
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns(this.saTokenProperty.getInterceptUrlList())
                .excludePathPatterns(openUrlList);
    }

    /**
     * 带有开放url注解的方法接口
     */
    private List<String> getApiOpenList() {
        List<String> apiOpenList = Lists.newArrayList();
        List<String> openUrlAnnotationClassPathList = this.saTokenProperty.getOpenUrlAnnotationClassPathList();
        openUrlAnnotationClassPathList.forEach(classPath -> {
            Map<String, Map<String, Object>> annotationUrlMap = this.annotationUtil.getAnnotationUrlMap(classPath, ApiOpen.class);
            annotationUrlMap.forEach((url, value) -> apiOpenList.add(url));
        });
        return apiOpenList;
    }


}
