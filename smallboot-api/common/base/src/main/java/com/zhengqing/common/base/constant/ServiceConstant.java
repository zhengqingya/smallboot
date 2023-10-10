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
     * 测试接口前缀 -- 统一放行
     */
    String SERVICE_API_PREFIX_TEST = "/api/test";
    /**
     * 各服务api前缀
     */
    String SERVICE_API_PREFIX_WEB_SYSTEM = SERVICE_API_PREFIX_WEB + "/system";
    String SERVICE_API_PREFIX_MINI_SYSTEM = SERVICE_API_PREFIX_MINI + "/system";
    String SERVICE_API_PREFIX_WEB_WXMP = SERVICE_API_PREFIX_WEB + "/wx/mp";
    String SERVICE_API_PREFIX_WEB_MALL = SERVICE_API_PREFIX_WEB + "/mall";
    String SERVICE_API_PREFIX_MINI_MALL = SERVICE_API_PREFIX_MINI + "/mall";
    String SERVICE_API_PREFIX_MINI_UMS = SERVICE_API_PREFIX_MINI + "/ums";
    String SERVICE_API_PREFIX_WEB_UMS = SERVICE_API_PREFIX_WEB + "/ums";
    String SERVICE_API_PREFIX_WEB_CMS = SERVICE_API_PREFIX_WEB + "/cms";
    String SERVICE_API_PREFIX_MINI_CMS = SERVICE_API_PREFIX_MINI + "/cms";

    /**
     * 微信公众号-接入
     */
    String SERVICE_API_PREFIX_WX_MP = "/wx/mp/portal";


}
