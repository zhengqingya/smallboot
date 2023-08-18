package com.zhengqing.common.base.constant;


import com.google.common.collect.Lists;

import java.util.List;

/**
 * <p>
 * 全局常用变量 - base
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/10/12 14:47
 */
public interface BaseConstant {

    /**
     * 第一级父类id
     */
    Integer PARENT_ID = 0;

    /**
     * 获取项目根目录
     */
    String PROJECT_ROOT_DIRECTORY = System.getProperty("user.dir").replaceAll("\\\\", "/");


    /**
     * 用户ID、用户名、消息上下文Key 【注：key名称一定不要和前端请求参数中的属性名一样，否则会拿不到真正的值！！！】
     */
    String CONTEXT_KEY_SYS_USER_ID = "smallboot_sys_user_id";
    String CONTEXT_KEY_UMS_USER_ID = "smallboot_ums_user_id";
    String CONTEXT_KEY_UMS_OPENID = "smallboot_ums_openid";
    String CONTEXT_KEY_USERNAME = "smallboot_username";
    String DEFAULT_CONTEXT_KEY_USER_ID = "0";
    String DEFAULT_CONTEXT_KEY_USERNAME = "未知";

    /**
     * 当前页、每页显示数量
     */
    String PAGE_NUM = "pageNum";
    String PAGE_SIZE = "pageSize";
    /**
     * 分页默认值
     */
    int DEFAULT_PAGE_NUM = 1;
    int DEFAULT_PAGE_SIZE = 10;

    /**
     * 请求头中的用户信息标识
     */
    String REQUEST_HEADER_TOKEN = "Authorization-smallboot";

    /**
     * rpc服务调用不需要封装返回值的api
     * 　　 ?　 =>　 匹配一个字符
     * 　　 *　 =>  匹配0个及以上字符
     * 　　 **　=>  匹配0个及以上目录
     */
    List<String> RETURN_VALUE_HANDLER_EXCLUDE_API_LIST = Lists.newArrayList(
            "*:/rpc/client/**/*",
            "POST:/oauth/token",
            "POST:/auth/oauth/token",
            "*:" + ServiceConstant.SERVICE_API_PREFIX_WX_MP + "/*",
            "*:/wx/pay/callback/*"
    );

}
