package com.zhengqing.common.base.context;


import com.zhengqing.common.base.constant.BaseConstant;
import com.zhengqing.common.base.enums.SysRoleCodeEnum;
import com.zhengqing.common.base.model.bo.JwtUserBO;

import java.util.List;

/**
 * <p> jwt自定义用户信息上下文 </p>
 *
 * @author zhengqingya
 * @description 请务必在请求结束时, 调用 @Method remove()
 * @date 2020/8/1 19:07
 */
public class JwtUserContext {

    public static final ThreadLocal<JwtUserBO> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(JwtUserBO jwtUserBO) {
        THREAD_LOCAL.set(jwtUserBO);
    }

    public static JwtUserBO get() {
        return THREAD_LOCAL.get();
    }

    public static String getUserId() {
        JwtUserBO jwtUserBO = get();
        if (jwtUserBO == null) {
            return BaseConstant.DEFAULT_CONTEXT_KEY_USER_ID;
        }
        return jwtUserBO.getUserId();
    }

    public static String getUsername() {
        JwtUserBO jwtUserBO = get();
        if (jwtUserBO == null) {
            return BaseConstant.DEFAULT_CONTEXT_KEY_USERNAME;
        }
        return jwtUserBO.getUsername();
    }

    public static String getNickname() {
        JwtUserBO jwtUserBO = get();
        if (jwtUserBO == null) {
            return "未知";
        }
        return jwtUserBO.getNickname();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    /**
     * 是否有超管角色
     */
    public static boolean hasSuperAdmin() {
        JwtUserBO jwtUserBO = JwtUserContext.get();
        if (jwtUserBO == null) {
            return false;
        }
        List<String> loginUserReRoleCodeList = jwtUserBO.getRoleCodeList();
        return loginUserReRoleCodeList.contains(SysRoleCodeEnum.超级管理员.getCode());
    }

    /**
     * 是否有系统管理员角色
     */
    public static boolean hasSystemAdmin() {
        JwtUserBO jwtUserBO = JwtUserContext.get();
        if (jwtUserBO == null) {
            return false;
        }
        List<String> loginUserReRoleCodeList = jwtUserBO.getRoleCodeList();
        return loginUserReRoleCodeList.contains(SysRoleCodeEnum.系统管理员.getCode());
    }

    /**
     * 是否有 超管角色 / 系统管理员角色
     */
    public static boolean hasSuperOrSystemAdmin() {
        return hasSuperAdmin() || hasSystemAdmin();
    }

}
