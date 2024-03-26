package com.zhengqing.mall.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 优惠券类型-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/25 10:03 下午
 */
@Getter
@AllArgsConstructor
public enum SmsCouponTypeEnum {

    满减券(1, "满减券"),
    折扣券(2, "折扣券"),
    代金券(3, "代金券"),
    一杯免费卷(4, "一杯免费卷"),
    第二杯半价卷(5, "第二杯半价卷");

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<SmsCouponTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(SmsCouponTypeEnum.values()));
    }

    public static SmsCouponTypeEnum getEnum(Integer type) {
        for (SmsCouponTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定优惠券类型！");
    }

}
