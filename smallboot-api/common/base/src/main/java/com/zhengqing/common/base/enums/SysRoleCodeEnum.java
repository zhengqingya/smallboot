package com.zhengqing.common.base.enums;

import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 角色枚举类 </p>
 *
 * @author zhengqingya
 * @description tips: 只列出系统默认自带的固定角色 或 一些特殊角色
 * @date 2020/11/28 23:35
 */
@Getter
@AllArgsConstructor
public enum SysRoleCodeEnum {

    超级管理员("super_admin", "超级管理员"),
    系统管理员("system_admin", "系统管理员"),
    租户管理员("tenant_admin", "租户管理员"),
    商户管理员("merchant_admin", "商户管理员");

    private final String code;
    private final String name;

    public static final List<SysRoleCodeEnum> LIST = Arrays.asList(SysRoleCodeEnum.values());


    public static SysRoleCodeEnum getEnum(String type) {
        for (SysRoleCodeEnum itemEnum : LIST) {
            if (itemEnum.getCode().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的角色code类型！");
    }

}
