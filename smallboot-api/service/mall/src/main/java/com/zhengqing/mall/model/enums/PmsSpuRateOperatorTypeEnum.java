package com.zhengqing.mall.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-操作人类型-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/25 10:03 下午
 */
@Getter
@AllArgsConstructor
public enum PmsSpuRateOperatorTypeEnum {

    /**
     * 买家
     */
    BUYER((byte) 1, "买家"),
    /**
     * 商家
     */
    SELLER((byte) 2, "商家");

    /**
     * 类型
     */
    private final Byte type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<PmsSpuRateOperatorTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(PmsSpuRateOperatorTypeEnum.values()));
    }

    /**
     * 根据指定操作人类型查找相应枚举
     */
    public static PmsSpuRateOperatorTypeEnum getEnum(Byte type) {
        for (PmsSpuRateOperatorTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定操作人类型！");
    }

}
