package com.zhengqing.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 菜单类型
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 17:16
 */
@Getter
@AllArgsConstructor
public enum SysMenuTypeEnum {

    菜单(1),
    按钮(2);

    private final Integer type;

}
