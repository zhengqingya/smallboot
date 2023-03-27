package com.zhengqing.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 用户关联角色枚举类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 23:35
 */
@Getter
@AllArgsConstructor
public enum SysUserReRoleEnum {

    凡人(1, "凡人"),
    超级管理员(9, "超级管理员");

    private final Integer roleId;
    private final String desc;

}
