package com.zhengqing.ums.constant;

import com.zhengqing.common.base.constant.BaseConstant;

/**
 * <p>
 * 全局常用变量 - ums
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/15 14:38
 */
public interface UmsConstant {

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ redis缓存系列 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    /**
     * 用户登录时的 sessionKey
     */
    String USER_SESSION_KEY = BaseConstant.BASE_PREFIX + ":mini:user:sessionkey:";


}
