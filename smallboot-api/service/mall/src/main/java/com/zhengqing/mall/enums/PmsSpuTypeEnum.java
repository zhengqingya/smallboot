package com.zhengqing.mall.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-商品类型-枚举类 </p>
 *
 * @author zhengqingya
 * @description 101->实物 102->虚拟-优惠券
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum PmsSpuTypeEnum {

    //  ================ ↓↓↓↓↓↓ 商品 ↓↓↓↓↓↓ ================
    /**
     * 实物
     */
    REAL((byte) 101, "实物"),
    /**
     * 虚拟-优惠券
     */
    VIRTUAL_COUPON((byte) 102, "虚拟-优惠券"),
    /**
     * 实物+虚拟  --  tips:暂未使用
     */
//    REAL_AND_VIRTUAL((byte) 203, "实物+虚拟")
    ;

    /**
     * 类型
     */
    private final Byte type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<PmsSpuTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(PmsSpuTypeEnum.values()));
    }

    /**
     * 根据指定商品类型查找相应枚举
     */
    public static PmsSpuTypeEnum getEnum(Byte type) {
        for (PmsSpuTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定商品类型！");
    }

    /**
     * 根据指定商品类型查找相应发货类型枚举
     */
    public static OmsOrderDeliverTypeEnum getDeliverTypeEnum(Byte type) {
        PmsSpuTypeEnum spuTypeEnum = getEnum(type);
        if (PmsSpuTypeEnum.REAL == spuTypeEnum) {
            return OmsOrderDeliverTypeEnum.EXPRESS;
        } else {
            return OmsOrderDeliverTypeEnum.NULL;
        }
    }


    /**
     * 根据指定商品类型判断是否为纯虚拟商品(不和实物包含一起)
     */
    public static Boolean isVirtual(Byte type) {
        PmsSpuTypeEnum spuTypeEnum = getEnum(type);
        return PmsSpuTypeEnum.VIRTUAL_COUPON == spuTypeEnum;
    }

    /**
     * 根据指定商品类型判断是否包含虚拟商品
     */
    public static Boolean isHasVirtual(Byte type) {
        PmsSpuTypeEnum spuTypeEnum = getEnum(type);
        return PmsSpuTypeEnum.VIRTUAL_COUPON == spuTypeEnum;
    }

}
