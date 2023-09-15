package com.zhengqing.system.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 系统-系统配置key枚举 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 9:50
 */
@Getter
@AllArgsConstructor
public enum SysConfigConfigKeyEnum {

    // ====================================== ↓↓↓↓↓↓ 商城 ↓↓↓↓↓↓ ======================================

    /**
     * 商城-订单-设置
     */
    MALL_ORDER_SET_AUTO_RECEIVE_MILLISECOND("mall_order_set_auto_receive_millisecond", "864000000", "发货后？毫秒后自动确认收货"),
    //    MALL_ORDER_SET_UN_PAY_CLOSE_MILLISECOND("mall_order_set_un_pay_close_millisecond", "600000", "待付款订单？毫秒后自动关闭"),
    MALL_ORDER_SET_BUYER_APPLY_AFTER_SALE_HANDLE_MILLISECOND("mall_order_set_buyer_apply_after_sale_handle_millisecond", "864000000", "买家发起售后申请？毫秒后，卖家未处理，自动关闭"),
    MALL_ORDER_SET_AFTER_SALE_BUYER_DELIVER_MILLISECOND("mall_order_set_after_sale_buyer_deliver_millisecond", "864000000", "待买家发货(买家申请售后，卖家同意后，买家未填写退货返回物流单号)？毫秒后自动关闭"),
    MALL_ORDER_SET_BUYER_APPLY_AFTER_SALE_MILLISECOND("mall_order_set_buyer_apply_after_sale_millisecond", "864000000", "买家确认收货？毫秒后无法发起售后申请"),
    MALL_ORDER_SET_STOCK_CHECK_TYPE("mall_order_set_stock_check_type", "1", "减库存设置（1：提交订单减库存 2：付款减库存）");

    private final String key;
    private final String value;
    private final String desc;

    private static final List<SysConfigConfigKeyEnum> LIST = Lists.newArrayList();

    /**
     * 商城-订单-设置-枚举数据
     */
    public static final List<SysConfigConfigKeyEnum> LIST_MALL_ORDER_SET = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(SysConfigConfigKeyEnum.values()));
        // 商城-订单-设置-枚举数据
        LIST_MALL_ORDER_SET.add(MALL_ORDER_SET_AUTO_RECEIVE_MILLISECOND);
//        LIST_MALL_ORDER_SET.add(MALL_ORDER_SET_UN_PAY_CLOSE_MILLISECOND);
        LIST_MALL_ORDER_SET.add(MALL_ORDER_SET_BUYER_APPLY_AFTER_SALE_HANDLE_MILLISECOND);
        LIST_MALL_ORDER_SET.add(MALL_ORDER_SET_AFTER_SALE_BUYER_DELIVER_MILLISECOND);
        LIST_MALL_ORDER_SET.add(MALL_ORDER_SET_BUYER_APPLY_AFTER_SALE_MILLISECOND);
        LIST_MALL_ORDER_SET.add(MALL_ORDER_SET_STOCK_CHECK_TYPE);
    }

    /**
     * 根据指定的系统配置key查找相应枚举类
     *
     * @param key 系统配置key
     * @return 系统配置key枚举
     * @author zhengqingya
     * @date 2021/8/19 9:50
     */
    public static SysConfigConfigKeyEnum getEnum(String key) {
        for (SysConfigConfigKeyEnum itemEnum : LIST) {
            if (itemEnum.getKey().equals(key)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的系统配置key！");
    }

}
