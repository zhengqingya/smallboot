package com.zhengqing.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 用户关联角色枚举类
 * </p>
 *
 * @author zhengqingya
 * @description tips: 只列出系统默认自带的固定角色
 * @date 2020/11/28 23:35
 */
@Getter
@AllArgsConstructor
public enum SysUserReRoleEnum {

    超级管理员(1, "超级管理员"),
    凡人(2, "凡人");

    private final Integer roleId;
    private final String desc;

}
