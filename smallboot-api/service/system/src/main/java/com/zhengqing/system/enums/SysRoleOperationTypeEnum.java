package com.zhengqing.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色操作类型
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 17:14
 */
@Getter
@AllArgsConstructor
public enum SysRoleOperationTypeEnum {

    添加或修改角色(1, "添加或修改角色"),
    角色关联菜单权限(2, "角色关联菜单权限");

    private final Integer type;
    private final String desc;

    private static final List<SysRoleOperationTypeEnum> LIST = Arrays.asList(SysRoleOperationTypeEnum.values());


    public static SysRoleOperationTypeEnum getEnum(Integer type) {
        for (SysRoleOperationTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        return 添加或修改角色;
    }

}
