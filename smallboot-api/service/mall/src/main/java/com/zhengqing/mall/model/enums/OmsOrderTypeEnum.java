package com.zhengqing.mall.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单类型-枚举类 </p>
 *
 * @author zhengqingya
 * @description 1->普通商品
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum OmsOrderTypeEnum {

    /**
     * 普通商品
     */
    PRODUCT((byte) 1, "普通商品");

    /**
     * 类型
     */
    private final Byte type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<OmsOrderTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderTypeEnum.values()));
    }

    /**
     * 根据指定订单类型查找相应枚举
     */
    public static OmsOrderTypeEnum getEnum(Byte type) {
        for (OmsOrderTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定订单类型！");
    }

}
