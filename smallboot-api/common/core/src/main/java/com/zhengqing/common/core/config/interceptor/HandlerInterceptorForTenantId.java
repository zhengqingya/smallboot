package com.zhengqing.common.core.config.interceptor;

import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.core.config.WebAppConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> 拦截器 -- 租户ID </p>
 *
 * @author zhengqingya
 * @description 注册使用参考 {@link WebAppConfig}
 * @date 2022/1/10 16:28
 */
public class HandlerInterceptorForTenantId implements HandlerInterceptor {

    /**
     * 租户ID字段名称
     */
    private static final String TENANT_ID = "TENANT_ID";
    /**
     * 是否排除租户ID标识
     */
    private static final String TENANT_ID_FLAG = "TENANT_ID_FLAG";

    /**
     * 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantId = request.getHeader(TENANT_ID);
        if (StringUtils.isNotBlank(tenantId) && StringUtils.isNumeric(tenantId)) {
            TenantIdContext.setTenantId(Integer.valueOf(tenantId));
        }
        // 是否排除租户ID标识
        String tenantIdFlag = request.getHeader(TENANT_ID_FLAG);
        String isTenantIdFlag = "true";
        if (StringUtils.isNotBlank(tenantIdFlag) && isTenantIdFlag.equals(tenantIdFlag)) {
            TenantIdContext.removeFlag();
        }
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后，生成视图之前执行。
     * 后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        TenantIdContext.remove();
    }

}
