package com.zhengqing.system.enums;

import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 系统-系统配置key枚举 </p>
 *
 * @author zhengqingya
 * @description tips: 同步 t_sys_config 表数据
 * @date 2021/8/19 9:50
 */
@Getter
@AllArgsConstructor
public enum SysConfigKeyEnum {

    // ====================================== ↓↓↓↓↓↓ 抖音小程序 ↓↓↓↓↓↓ ======================================
//    DOUYIN_COMPONENT_APPID("douyin_component_appid", null, "抖音服务商平台-第三方小程序应用appid"),
//    DOUYIN_COMPONENT_APPSECRET("douyin_component_appsecret", null, "抖音服务商平台-第三方小程序应用appsecret"),
//    DOUYIN_TP_TOKEN("douyin_tp_token", null, "抖音服务商平台-消息验证TOKEN"),
//    DOUYIN_ENCODING_AES_KEY("douyin_encoding_aes_key", null, "抖音服务商平台-消息加密解密KEY"),

    // ====================================== ↓↓↓↓↓↓ 商城 ↓↓↓↓↓↓ ======================================

    LBS_QQ_KEY("lbs_qq_key", null, "腾讯地图key"),
    MALL_INDEX_SLIDE_IMG_LIST("mall_index_slide_img_list", null, "商城首页轮播图"),

    /**
     * 商城-订单-设置
     */
    MALL_ORDER_SET_AUTO_RECEIVE_MILLISECOND("mall_order_set_auto_receive_millisecond", "864000000", "发货后？毫秒后自动确认收货"),
    MALL_ORDER_SET_UN_PAY_CLOSE_MILLISECOND("mall_order_set_un_pay_close_millisecond", "600000", "待付款订单？毫秒后自动关闭"),
    MALL_ORDER_SET_BUYER_APPLY_AFTER_SALE_HANDLE_MILLISECOND("mall_order_set_buyer_apply_after_sale_handle_millisecond", "864000000", "买家发起售后申请？毫秒后，卖家未处理，自动关闭"),
    MALL_ORDER_SET_AFTER_SALE_BUYER_DELIVER_MILLISECOND("mall_order_set_after_sale_buyer_deliver_millisecond", "864000000", "待买家发货(买家申请售后，卖家同意后，买家未填写退货返回物流单号)？毫秒后自动关闭"),
    MALL_ORDER_SET_BUYER_APPLY_AFTER_SALE_MILLISECOND("mall_order_set_buyer_apply_after_sale_millisecond", "864000000", "买家确认收货？毫秒后无法发起售后申请"),
    MALL_ORDER_SET_STOCK_CHECK_TYPE("mall_order_set_stock_check_type", "1", "减库存设置（1：提交订单减库存 2：付款减库存）");

    private final String key;
    /**
     * 初始化时默认值
     */
    private final String value;
    private final String desc;

    private static final List<SysConfigKeyEnum> LIST = Arrays.asList(SysConfigKeyEnum.values());

    /**
     * 根据指定的系统配置key查找相应枚举类
     *
     * @param key 系统配置key
     * @return 系统配置key枚举
     * @author zhengqingya
     * @date 2021/8/19 9:50
     */
    public static SysConfigKeyEnum getEnum(String key) {
        for (SysConfigKeyEnum itemEnum : LIST) {
            if (itemEnum.getKey().equals(key)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的系统配置key！");
    }

}
