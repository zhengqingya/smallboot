package com.zhengqing.system.constant;

import com.zhengqing.common.base.constant.BaseConstant;

/**
 * <p>
 * 全局常用变量 - system
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/15 14:38
 */
public interface SystemConstant {

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ redis缓存系列 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    /**
     * 数据字典缓存
     */
    String CACHE_SYS_DICT_PREFIX = BaseConstant.BASE_PREFIX + ":system:dict:";
    /**
     * 系统配置缓存
     */
    String CACHE_SYS_CONFIG_PREFIX = BaseConstant.BASE_PREFIX + ":system:config:";
    /**
     * 系统缓存
     */
    String CACHE_SYS_MENU_TREE = BaseConstant.BASE_PREFIX + ":system:sys_menu_tree";
    /**
     * 个人缓存
     */
    String CACHE_SYS_USER_INFO_PREFIX = BaseConstant.BASE_PREFIX + ":system:sys_user_info_";
    String CACHE_SYS_PERMISSION_PREFIX = BaseConstant.BASE_PREFIX + ":system:sys_permission_";


}
