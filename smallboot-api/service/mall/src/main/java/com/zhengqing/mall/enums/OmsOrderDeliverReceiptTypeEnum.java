package com.zhengqing.mall.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单-配送-收货类型-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum OmsOrderDeliverReceiptTypeEnum {

    /**
     * 手动收货
     */
    HAND((byte) 1, "手动收货"),
    /**
     * 自动收货
     */
    AUTO((byte) 2, "自动收货");

    /**
     * 类型
     */
    private final Byte type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<OmsOrderDeliverReceiptTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderDeliverReceiptTypeEnum.values()));
    }

    /**
     * 根据指定配送收货类型查找相应枚举
     */
    public static OmsOrderDeliverReceiptTypeEnum getEnum(Byte type) {
        for (OmsOrderDeliverReceiptTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定配送收货类型！");
    }

}
