package com.zhengqing.system.enums;

import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 小程序状态 枚举 </p>
 *
 * @author zhengqingya
 * @description tips: value变动时需配置前端修改！！！
 * @date 2021/8/19 9:50
 */
@Getter
@AllArgsConstructor
public enum SysMerchantAppStatusEnum {

    提交代码(10, "提交代码"),
    提审代码(20, "提审代码"),
    提审中(21, "提审中"),
    撤回审核(22, "撤回审核"),
    审核通过(31, "审核通过"),
    审核不通过(32, "审核不通过"),
    发布代码(50, "发布代码"),
    发布中(51, "发布中"),
    已发布(52, "已发布");

    private final Integer status;
    private final String desc;

    private static final List<SysMerchantAppStatusEnum> LIST = Arrays.asList(SysMerchantAppStatusEnum.values());

    public static SysMerchantAppStatusEnum getEnum(Integer status) {
        for (SysMerchantAppStatusEnum itemEnum : LIST) {
            if (itemEnum.getStatus().equals(status)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的app状态！");
    }

}
