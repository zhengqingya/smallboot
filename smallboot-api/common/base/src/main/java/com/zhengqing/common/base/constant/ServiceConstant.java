package com.zhengqing.common.base.constant;

/**
 * <p> 全局常用变量 - 工程使用 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/7/20 18:16
 */
public interface ServiceConstant {


    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ service ↓↓↓↓↓↓ ============================
    // ===============================================================================

    String SERVICE_BASE_PACKAGE = "com.zhengqing";
    /**
     * api基础前缀
     */
    String SERVICE_API_PREFIX_WEB = "/web/api";
    String SERVICE_API_PREFIX_MINI = "/mini/api";
    /**
     * 各服务api前缀
     */
    String SERVICE_API_PREFIX_WEB_SYSTEM = SERVICE_API_PREFIX_WEB + "/system";
    String SERVICE_API_PREFIX_WEB_WXMP = SERVICE_API_PREFIX_WEB + "/wx/mp";


}
