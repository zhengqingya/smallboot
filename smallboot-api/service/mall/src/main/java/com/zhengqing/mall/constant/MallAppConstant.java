package com.zhengqing.mall.constant;

/**
 * <p>
 * 全局常用变量 - 商城
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/17 10:03
 */
public interface MallAppConstant {

    /**
     * 父id
     */
    String PARENT_ID = "0";

    /**
     * 确认收货后-售后处理可申请处理时间 -- 7天
     */
    long BUYER_APPLY_AFTER_SALE_MILLISECOND = 604800000;
    /**
     * 售后自动关闭时间 -- 7天
     */
    long AUTO_CLOSE_AFTER_MILLISECOND = 604800000;
    /**
     * 自动收货时间 -- 7天
     */
    long AUTO_RECEIPT_MILLISECOND = 604800000;

}
