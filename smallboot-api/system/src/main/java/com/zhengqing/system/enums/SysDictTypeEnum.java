package com.zhengqing.system.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 数据字典枚举
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/12 0:01
 */
@Getter
@AllArgsConstructor
public enum SysDictTypeEnum {

    权限按钮("permission_btn", "权限按钮"),
    文件后缀名("file_suffix", "文件后缀名"),
    Element_Icon图标("element_icon", "Element_Icon图标"),
    小工具_爬虫_CSDN文章导出数据类型("st_crawler_csdn_export_data_type", "小工具_爬虫_CSDN文章导出数据类型"),
    小工具_数据库_数据源类型("st_db_data_source_type", "小工具_数据库_数据源类型"),
    第三方帐号授权类型("oauth_type", "第三方帐号授权类型"),

    // ====================================== ↓↓↓↓↓↓ 商城 ↓↓↓↓↓↓ ======================================

    /**
     * 商城-商品-tab
     */
    MALL_SPU_TAB_CONDITION("mall_spu_tab_condition", "商城-商品-tab"),
    /**
     * 商城-商品-品类大标题
     */
    MALL_SPU_CATEGORY_BIG_TITLE("mall_spu_category_big_title", "商城-商品-品类大标题"),
    /**
     * 商城-商品-按钮文案
     */
    MALL_SPU_BUTTON_DOC("mall_spu_button_doc", "商城-商品-按钮文案"),
    /**
     * 商城-商品-服务
     */
    MALL_SPU_SERVICE("mall_spu_service", "商城-商品-服务"),
    /**
     * 商城-商品-说明
     */
    MALL_SPU_EXPLAIN("mall_spu_explain", "商城-商品-说明"),
    /**
     * 商城-订单-tab
     */
    MALL_ORDER_TAB_CONDITION_WEB("mall_order_tab_condition_web", "商城-订单-tab-web"),
    MALL_ORDER_TAB_CONDITION_MINI("mall_order_tab_condition_mini", "商城-订单-tab-mini"),
    /**
     * 商城-售后-tab
     */
    MALL_ORDER_AFTER_SALE_TAB_CONDITION("mall_order_after_sale_tab_condition", "商城-售后-tab"),
    /**
     * 商城-订单-发货微信消息通知
     */
    MALL_ORDER_DELIVER_WX_MSG_NOTICE("mall_order_deliver_wx_msg_notice", "商城-订单-发货微信消息通知");

    private final String code;
    private final String desc;

    private static final List<SysDictTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(SysDictTypeEnum.values()));
    }

    /**
     * 根据指定的数据字典编码查找相应枚举类
     *
     * @param code 数据字典编码
     * @return 数据字典枚举信息
     * @author zhengqingya
     * @date 2020/8/30 2:56
     */
    public static SysDictTypeEnum getEnum(String code) {
        for (SysDictTypeEnum itemEnum : LIST) {
            if (itemEnum.getCode().equals(code)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的数据字典编码！");
    }

}
