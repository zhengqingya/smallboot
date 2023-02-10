package com.zhengqing.common.web.util;

import cn.hutool.core.convert.Convert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * <p>
 * 客户端工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/8/1 18:22
 */
public class ServletUtil {

    private static final String X_REAL_IP = "X-Real-IP";
    private static final String X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String X_FORWARDED_FOR_SPLIT_SYMBOL = ",";

    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            String requestHeaderResult = request.getHeader(name);
            if (requestHeaderResult != null) {
                requestHeaderResult = URLDecoder.decode(requestHeaderResult);
            }
            return request.getParameter(name) == null ? requestHeaderResult : request.getParameter(name);
        }
        return null;
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name, String defaultValue) {
        return StringUtils.defaultString(getParameter(name), defaultValue);
    }

    /**
     * 获取int参数
     */
    public static int getParameterToInt(String name) {
        return Convert.toInt(getParameter(name));
    }

    /**
     * 获取int参数
     */
    public static int getParameterToInt(String name, int defaultValue) {
        return Convert.toInt(getParameter(name), defaultValue);
    }

    /**
     * 获取long参数
     */
    public static Long getParameterToLong(String name, Long defaultValue) {
        return Convert.toLong(getParameter(name), defaultValue);
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取项目webroot根目录
     */
    public static String getWebRootPath() {
        return getRequest().getSession().getServletContext().getRealPath("/");
    }

    /**
     * 是否是Ajax异步请求
     *
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1) {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.containsIgnoreCase(uri, ".json") || StringUtils.containsIgnoreCase(uri, ".xml")) {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (StringUtils.containsIgnoreCase(ajax, ".json") || StringUtils.containsIgnoreCase(ajax, ".xml")) {
            return true;
        }
        return false;
    }

    /**
     * 获取实际请求地址
     *
     * @param request
     * @return IP地址
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader(X_FORWARDED_FOR);
        if (!StringUtils.isBlank(xForwardedFor)) {
            return xForwardedFor.split(X_FORWARDED_FOR_SPLIT_SYMBOL)[0].trim();
        }
        String nginxHeader = request.getHeader(X_REAL_IP);
        return StringUtils.isBlank(nginxHeader) ? request.getRemoteAddr() : nginxHeader;
    }
}
