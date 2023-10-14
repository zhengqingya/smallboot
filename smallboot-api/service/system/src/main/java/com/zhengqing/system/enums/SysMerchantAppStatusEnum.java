package com.zhengqing.system.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * <p> 小程序状态 枚举 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 9:50
 */
@Getter
@AllArgsConstructor
public enum SysMerchantAppStatusEnum {

    提交代码(10, "提交代码"),
    提审中(20, "提审中"),
    撤回审核(21, "撤回审核"),
    审核通过(31, "审核通过"),
    审核不通过(32, "审核不通过"),
    发布中(50, "发布中"),
    已发布(51, "已发布");

    private final Integer type;
    private final String desc;

    private static final List<SysMerchantAppStatusEnum> LIST = Lists.newArrayList();

    public static SysMerchantAppStatusEnum getEnum(Integer type) {
        for (SysMerchantAppStatusEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的商户类型！");
    }

}
