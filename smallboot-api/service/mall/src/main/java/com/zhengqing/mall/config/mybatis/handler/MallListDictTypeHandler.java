package com.zhengqing.mall.config.mybatis.handler;

import com.zhengqing.common.db.config.mybatis.handler.DbJsonTypeHandler;
import com.zhengqing.mall.common.model.bo.MallDictBO;

/**
 * <p> 自定义类型处理器 </p>
 *
 * @author zhengqingya
 * @description 字典
 * @date 2022/6/6 11:26
 */
public class MallListDictTypeHandler extends DbJsonTypeHandler {

    public MallListDictTypeHandler(Class<?> type) {
        super(type, MallDictBO.class);
    }

}