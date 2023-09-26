package com.zhengqing.mall.constant;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 全局常用变量 - 商城 - 缓存
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/17 10:03
 */
public interface MallRedisConstant {

    // ====================================== ↓↓↓↓↓↓ 门店 ↓↓↓↓↓↓ ======================================

    /**
     * 门店位置
     */
    String SHOP_GEO_LOCATION = "smallboot:mall:shop:geo_location:";

    // ====================================== ↓↓↓↓↓↓ 商品 ↓↓↓↓↓↓ ======================================

    /**
     * 某商品预售需提醒的用户
     */
    String SPU_PRESELL_REMIND = "smallboot:mall:spu:presell_remind:";
    /**
     * 某商品预售需提醒的用户 -> 过期时间30天
     */
    long SPU_PRESELL_REMIND_TIME_DEFAULT = 30;

    /**
     * 商品sku库存
     */
    String SKU_STOCK = "smallboot:mall:sku:stock:";
    /**
     * 商品sku库存时间 - 60分钟
     */
    long SKU_STOCK_TIME = 60 * 60;
    /**
     * 商品sku库存锁
     */
    String SKU_STOCK_LOCK = "smallboot:mall:sku:stock:lock:";
    /**
     * 商品sku库存锁等待时间
     */
    int SKU_STOCK_LOCK_TIME_WAIT = 3;
    /**
     * 商品sku库存锁时间
     */
    int SPU_STOCK_LOCK_TIME = 60;
    /**
     * 商品sku库存锁-时间单位-秒
     */
    TimeUnit SKU_STOCK_TIME_UNIT = TimeUnit.SECONDS;

    // ====================================== ↓↓↓↓↓↓ mini-购物车 ↓↓↓↓↓↓ ======================================

    /**
     * 每一个用户的购物车数据
     */
    String MINI_CART = "smallboot:mall:mini:cart:";
    String MINI_CART_LOCK = "smallboot:mall:mini:cart_lock:";

    // ====================================== ↓↓↓↓↓↓ 订单 ↓↓↓↓↓↓ ======================================

    /**
     * 订单自动收货时间 - 天
     */
    String ORDER_AUTO_RECEIVE_TIME = "smallboot:mall:order:auto_receive_time:";
    /**
     * 默认订单自动收货时间 -> 30天
     */
    int ORDER_AUTO_RECEIVE_TIME_DEFAULT = 30;

    /**
     * 订单收货时间 - 秒
     */
    String ORDER_RECEIVE_TIME = "smallboot:mall:order:receive_time:";

    /**
     * 订单待支付
     */
    String ORDER_UN_PAY = "smallboot:mall:order:un:pay:";
    /**
     * 订单待支付时间 - 10分钟
     */
    long ORDER_UN_PAY_TIME = 10 * 60;

    // ====================================== ↓↓↓↓↓↓ OTHER ↓↓↓↓↓↓ ======================================

}
