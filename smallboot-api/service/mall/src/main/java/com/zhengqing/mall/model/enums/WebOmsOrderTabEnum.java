package com.zhengqing.mall.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单-售后-tab-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum WebOmsOrderTabEnum {

    /**
     * 全部
     */
    ALL((byte) -1, 1, "全部"),
    /**
     * 待支付
     */
    UN_PAY((byte) 1, 2, "待付款"),
    /**
     * 已取消
     */
    CANC((byte) 2, 6, "已取消"),
    /**
     * 待发货
     */
    UN_BILL((byte) 3, 3, "待发货"),
    /**
     * 待收货
     */
    BILL((byte) 4, 4, "待收货"),
    /**
     * 已完成
     */
    FINISH((byte) 5, 5, "已完成");


    /**
     * 值
     */
    private final Byte value;
    /**
     * tab条件查询时的排序值
     */
    private final Integer tabSort;
    /**
     * 描述
     */
    private final String name;

    public static final List<WebOmsOrderTabEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(WebOmsOrderTabEnum.values()));
    }

    /**
     * 根据指定tab查找相应枚举
     */
    public static WebOmsOrderTabEnum getEnum(Byte value) {
        for (WebOmsOrderTabEnum itemEnum : LIST) {
            if (itemEnum.getValue().equals(value)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定订单tab！");
    }

}
