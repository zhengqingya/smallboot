package com.zhengqing.common.db.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> mybatis-plus 数据权限类型枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/1/10 17:42
 */
@Getter
@AllArgsConstructor
public enum DataPermissionTypeEnum {

    /**
     * 全部可见
     */
    全部可见(1, "全部可见"),
    /**
     * 本人可见 -- 查看自己的数据
     */
    本人可见(2, "本人可见"),
    /**
     * 所在部门可见
     */
    所在部门可见(3, "所在部门可见"),
    /**
     * 所在部门及子级可见
     */
    所在部门及子级可见(4, "所在部门及子级可见"),
    /**
     * 所在角色
     */
    所在角色(5, "所在角色"),
    /**
     * 所在角色以及下级角色
     */
    所在角色以及下级角色(6, "所在角色以及下级角色"),
    /**
     * 自定义
     */
    自定义(10, "自定义"),
    /**
     * 自定义sql过滤
     */
//    自定义sql过滤(1000, "自定义sql过滤")
    ;

    /**
     * 类型
     */
    private final int type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<DataPermissionTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(DataPermissionTypeEnum.values()));
    }

    /**
     * 根据指定的规则类型查找相应枚举类
     *
     * @param type 类型
     * @return 类型枚举信息
     * @author zhengqingya
     * @date 2022/1/10 17:43
     */
    public static DataPermissionTypeEnum getEnum(int type) {
        for (DataPermissionTypeEnum itemEnum : LIST) {
            if (itemEnum.getType() == type) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定数据权限枚举信息!");
    }

}
