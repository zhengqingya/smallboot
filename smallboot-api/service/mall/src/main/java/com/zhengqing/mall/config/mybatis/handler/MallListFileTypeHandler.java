package com.zhengqing.mall.config.mybatis.handler;

import com.zhengqing.common.db.config.mybatis.handler.DbJsonTypeHandler;
import com.zhengqing.mall.model.bo.MallFileBO;

/**
 * <p> 自定义类型处理器 </p>
 *
 * @author zhengqingya
 * @description 文件
 * @date 2022/6/6 11:26
 */
public class MallListFileTypeHandler extends DbJsonTypeHandler {

    public MallListFileTypeHandler(Class<?> type) {
        super(type, MallFileBO.class);
    }

}