package com.zhengqing.common.base.context;


import com.zhengqing.common.base.model.bo.JwtUserBO;

/**
 * <p> jwt自定义用户信息上下文 </p>
 *
 * @author zhengqingya
 * @description 请务必在请求结束时, 调用 @Method remove()
 * @date 2020/8/1 19:07
 */
public class JwtUserContext {

    public static final ThreadLocal<JwtUserBO> THREAD_LOCAL = new ThreadLocal<>();

    public static JwtUserBO get() {
        return THREAD_LOCAL.get();
    }

    public static void set(JwtUserBO jwtUserBO) {
        THREAD_LOCAL.set(jwtUserBO);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
