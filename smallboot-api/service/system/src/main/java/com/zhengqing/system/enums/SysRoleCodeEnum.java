package com.zhengqing.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 角色枚举类 </p>
 *
 * @author zhengqingya
 * @description tips: 只列出系统默认自带的固定角色
 * @date 2020/11/28 23:35
 */
@Getter
@AllArgsConstructor
public enum SysRoleCodeEnum {

    超级管理员("super_admin", "超级管理员"),
    租户管理员("tenant_admin", "租户管理员"),
    商户管理员("merchant_admin", "商户管理员");

    private final String code;
    private final String name;

}
