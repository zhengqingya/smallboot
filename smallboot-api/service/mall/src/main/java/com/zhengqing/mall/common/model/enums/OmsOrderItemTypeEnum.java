package com.zhengqing.mall.common.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单-商品类型-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum OmsOrderItemTypeEnum {

    /**
     * 实物
     */
    REAL((byte) 1, "实物"),
    /**
     * 虚拟
     */
    VIRTUAL((byte) 2, "虚拟");

    /**
     * 类型
     */
    private final Byte type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<OmsOrderItemTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderItemTypeEnum.values()));
    }

    /**
     * 根据指定商品类型查找相应枚举
     */
    public static OmsOrderItemTypeEnum getEnum(Byte type) {
        for (OmsOrderItemTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定商品类型！");
    }

    /**
     * 根据指定商品类型查找相应枚举 -- 得到前端需要显示的值
     */
    public static OmsOrderItemTypeEnum getEnumBySpuType(Byte type) {
        if (PmsSpuTypeEnum.isVirtual(type)) {
            return OmsOrderItemTypeEnum.VIRTUAL;
        } else {
            return OmsOrderItemTypeEnum.REAL;
        }
    }


}
