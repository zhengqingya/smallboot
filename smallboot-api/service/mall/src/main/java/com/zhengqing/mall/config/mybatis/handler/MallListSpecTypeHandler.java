package com.zhengqing.mall.config.mybatis.handler;

import com.zhengqing.common.db.config.mybatis.handler.DbJsonTypeHandler;
import com.zhengqing.mall.common.model.bo.PmsSkuSpecBO;

/**
 * <p> 自定义类型处理器 </p>
 *
 * @author zhengqingya
 * @description 规格
 * @date 2022/6/6 11:26
 */
public class MallListSpecTypeHandler extends DbJsonTypeHandler {

    public MallListSpecTypeHandler(Class<?> type) {
        super(type, PmsSkuSpecBO.class);
    }

}