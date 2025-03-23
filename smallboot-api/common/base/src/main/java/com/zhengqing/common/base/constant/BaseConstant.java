package com.zhengqing.common.base.constant;


import com.google.common.collect.Lists;

import java.util.List;

/**
 * <p> 全局常用变量 - base </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/10/12 14:47
 */
public interface BaseConstant {

    /**
     * YML配置基础前缀
     * 方便新项目统一修改配置
     */
    String YML_BASE_PREFIX = "smallboot";

    /**
     * 项目基础前缀，不要带：等特殊分割符
     * 用于文件上传基础前缀、redis key前缀等等
     */
    String BASE_PREFIX = "smallboot";

    /**
     * 当前项目名
     * 本地路径可能会基于此基本路径
     */
    String CURRENT_PROJECT_NAME = "smallboot-api";

    /**
     * 第一级父类id
     */
    Integer PARENT_ID = 0;
    Long PARENT_ID_LONG = 0L;


    /**
     * 项目租户 -- id、超级套餐
     */
    Integer SMALL_BOOT_TENANT_ID = 1;
    Integer SMALL_BOOT_TENANT_ID_PACKAGE_ID = 1;

    /**
     * 获取项目根目录
     */
    String PROJECT_ROOT_DIRECTORY = System.getProperty("user.dir").replaceAll("\\\\", "/");


    /**
     * 用户ID、用户名、消息上下文Key 【注：key名称一定不要和前端请求参数中的属性名一样，否则会拿不到真正的值！！！】
     */
    String CONTEXT_KEY_SYS_USER_ID = "zq_sys_user_id";
    String CONTEXT_KEY_UMS_USER_ID = "zq_ums_user_id";
    String CONTEXT_KEY_UMS_OPENID = "zq_ums_openid";
    String CONTEXT_KEY_USERNAME = "zq_username";
    String DEFAULT_CONTEXT_KEY_USER_ID = "0";
    String DEFAULT_CONTEXT_KEY_USERNAME = "未知";

    /**
     * 服务调用不需要封装返回值的api
     * 　　 ?　 =>　 匹配一个字符
     * 　　 *　 =>  匹配0个及以上字符
     * 　　 **　=>  匹配0个及以上目录
     */
    List<String> RETURN_VALUE_HANDLER_EXCLUDE_API_LIST = Lists.newArrayList(
            "*:/rpc/client/**/*",
//            "POST:/oauth/token",
//            "POST:/auth/oauth/token",
            "*:" + ServiceConstant.SERVICE_API_PREFIX_WX_MP + "/*",
            "*:/wx/pay/callback/*"
    );

}
