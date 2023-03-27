package com.zhengqing.mall.common.model.enums;

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
 * @description 注：value值变动时需通知前端联调修改！！！
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum MiniOmsOrderTabEnum {

    /**
     * 全部
     */
    ALL((byte) -1, 1, "全部"),
    /**
     * 待支付
     */
    UN_PAY((byte) 1, 2, "待付款"),
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
    FINISH((byte) 5, 5, "已完成"),
    /**
     * 退款/售后 -- 注：mini端此tab栏数据单独从售后订单拉取数据
     */
    AFTER_SALE((byte) 100, 6, "退款/售后");


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

    public static final List<MiniOmsOrderTabEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(MiniOmsOrderTabEnum.values()));
    }

    /**
     * 根据指定tab查找相应枚举
     */
    public static MiniOmsOrderTabEnum getEnum(Byte value) {
        for (MiniOmsOrderTabEnum itemEnum : LIST) {
            if (itemEnum.getValue().equals(value)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定订单tab！");
    }

}
