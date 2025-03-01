package com.zhengqing.common.base.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    超级管理员("super_admin", 1, "超级管理员"),
    系统管理员("system_admin", 2, "系统管理员"),
    租户管理员("tenant_admin", 3, "租户管理员"),
    主播("anchor", 4, "主播"),
//    商户管理员("merchant_admin", 4, "商户管理员")
    ;

    private final String code;
    private final Integer sort;
    private final String name;

    public static final List<SysRoleCodeEnum> LIST = Arrays.asList(SysRoleCodeEnum.values());
    public static final List<String> CODE_LIST = Arrays.asList(SysRoleCodeEnum.values()).stream().map(SysRoleCodeEnum::getCode).collect(Collectors.toList());
    /**
     * 特殊角色 -- 不能删除、编辑基本信息
     */
    public static final List<String> SPECIAL_CODE_LIST = Lists.newArrayList(超级管理员.getCode(), 系统管理员.getCode(), 租户管理员.getCode());


    public static SysRoleCodeEnum getEnum(String type) {
        for (SysRoleCodeEnum itemEnum : LIST) {
            if (itemEnum.getCode().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的角色code类型！");
    }

    public static boolean isSpecialRole(String code) {
        return SPECIAL_CODE_LIST.contains(code);
    }

}
