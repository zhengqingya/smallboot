package com.zhengqing.common.base.context;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> IP上下文 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/30 9:24 下午
 */
@Slf4j
public class IpContext {

    public static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void setIp(String ip) {
        THREAD_LOCAL.set(ip);
    }

    public static String getIp() {
        return THREAD_LOCAL.get();
    }


    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
