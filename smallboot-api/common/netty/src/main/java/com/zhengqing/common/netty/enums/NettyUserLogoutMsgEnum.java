package com.zhengqing.common.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 用户登出消息提示 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/2/26 15:31
 */
@Getter
@AllArgsConstructor
public enum NettyUserLogoutMsgEnum {

    JWT_AUTH_FAIL("权限认证失败！"),
    UPDATE_ROLE_PERM("用户角色信息变更，需要重新登录！"),
    MULTIPLE_ENTRY("您已在其他地方登陆，将被强制下线"),
    ;

    /**
     * 描述
     */
    private final String desc;


}

