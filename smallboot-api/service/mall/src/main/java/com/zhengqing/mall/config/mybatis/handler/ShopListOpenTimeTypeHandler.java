package com.zhengqing.mall.config.mybatis.handler;

import com.zhengqing.common.db.config.mybatis.handler.DbJsonTypeHandler;
import com.zhengqing.mall.model.bo.SmsShopOpenTimeBO;

/**
 * <p> 自定义类型处理器 </p>
 *
 * @author zhengqingya
 * @description 门店营业时间
 * @date 2022/6/6 11:26
 */
public class ShopListOpenTimeTypeHandler extends DbJsonTypeHandler {

    public ShopListOpenTimeTypeHandler(Class<?> type) {
        super(type, SmsShopOpenTimeBO.class);
    }

}