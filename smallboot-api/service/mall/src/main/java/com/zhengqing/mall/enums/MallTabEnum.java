package com.zhengqing.mall.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-tab-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum MallTabEnum {

    MALL_SPU_TAB_CONDITION("mall_spu_tab_condition", "商城-商品-tab"),
    MALL_ORDER_TAB_CONDITION_WEB("mall_order_tab_condition_web", "商城-订单-tab-web"),
    MALL_ORDER_TAB_CONDITION_MINI("mall_order_tab_condition_mini", "商城-订单-tab-mini"),
    MALL_ORDER_AFTER_SALE_TAB_CONDITION("mall_order_after_sale_tab_condition", "商城-售后-tab");


    /**
     * 类型
     */
    private final String type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<MallTabEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(MallTabEnum.values()));
    }

    /**
     * 根据指定类型查找相应枚举
     */
    public static MallTabEnum getEnum(Byte payType) {
        for (MallTabEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(payType)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定tab类型！");
    }

}
