package com.zhengqing.common.sdk.douyin.util;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p> 抖音接口请求工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/28 15:40
 */
@Slf4j
@Component
public class DyBaseApiUtil {

    /**
     * post请求
     *
     * @param url         请求地址
     * @param requestBody 请求参数体
     * @param clazz       响应类
     * @return 响应数据
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static <T> T basePost(String url, Object requestBody, Class<T> clazz) {
        String body = JSONUtil.toJsonStr(requestBody);
        String resultStr = HttpUtil.post(url, body);
        log.debug("[抖音] \n 请求url: 【{}】 \n 请求体: 【{}】 \n 响应数据：【{}】", url, body, resultStr);
        return JSONUtil.toBean(resultStr, clazz);
    }

    /**
     * post请求
     *
     * @param url         请求地址
     * @param requestBody 请求参数体
     * @param headerMap   请求头参数
     * @param clazz       响应类
     * @return 响应数据
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static <T> T basePost(String url,
                                 Object requestBody,
                                 Map<String, String> headerMap,
                                 Class<T> clazz) {
        String body = HttpUtil.createPost(url)
                .addHeaders(headerMap)
                .body(JSONUtil.toJsonStr(requestBody))
                .execute().body();
        log.debug("[抖音] \n 请求url: 【{}】 \n 请求体: 【{}】 \n 请求头: 【{}】 \n 响应数据：【{}】", url, JSONUtil.toJsonStr(requestBody), headerMap, body);
        return JSONUtil.toBean(body, clazz);
    }

    /**
     * post请求
     *
     * @param url           请求地址
     * @param requestParams 请求参数
     * @param clazz         响应类
     * @return 响应数据
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static <T> T basePostParams(String url,
                                       Object requestParams,
                                       Class<T> clazz) {
        String body = HttpUtil.createPost(url + "?" + HttpUtil.toParams(JSONUtil.toBean(JSONUtil.toJsonStr(requestParams), Map.class))).execute().body();
        log.debug("[抖音] \n 请求url: 【{}】 \n 请求form参数: 【{}】 \n 响应数据：【{}】", url, JSONUtil.toJsonStr(requestParams), body);
        return JSONUtil.toBean(body, clazz);
    }

    /**
     * post请求
     *
     * @param url           请求地址
     * @param requestParams 请求参数
     * @param requestBody   请求参数体
     * @param clazz         响应类
     * @return 响应数据
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static <T> T basePostParamsBody(String url,
                                           Object requestParams,
                                           Object requestBody,
                                           Class<T> clazz) {
        String body = HttpUtil.createPost(url + "?" + HttpUtil.toParams(JSONUtil.toBean(JSONUtil.toJsonStr(requestParams), Map.class)))
                .body(JSONUtil.toJsonStr(requestBody))
                .execute().body();
        log.debug("[抖音] \n 请求url: 【{}】 \n 请求form参数: 【{}】 \n 请求体: 【{}】 \n 响应数据：【{}】", url, JSONUtil.toJsonStr(requestParams), JSONUtil.toJsonStr(requestBody), body);
        return JSONUtil.toBean(body, clazz);
    }

    /**
     * get请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param clazz  响应类
     * @return 响应数据
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static <T> T baseGet(String url, Object params, Class<T> clazz) {
        String body = HttpUtil.createGet(url).form(BeanUtil.beanToMap(params)).execute().body();
        log.debug("[抖音] \n 请求url: 【{}】 \n 请求参数: 【{}】 \n 响应数据：【{}】", url, JSONUtil.toJsonStr(params), body);
        return JSONUtil.toBean(body, clazz);
    }

    /**
     * get请求
     *
     * @param url       请求地址
     * @param params    请求参数
     * @param headerMap 请求头参数
     * @param clazz     响应类
     * @return 响应数据
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static <T> T baseGet(String url, Object params, Map<String, String> headerMap, Class<T> clazz) {
        String body = HttpUtil.createGet(url).addHeaders(headerMap).form(BeanUtil.beanToMap(params)).execute().body();
        log.debug("[抖音] \n 请求url: 【{}】 \n 请求头: 【{}】 \n 响应数据：【{}】", url, headerMap, body);
        return JSONUtil.toBean(body, clazz);
    }

}
