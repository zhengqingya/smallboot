package com.zhengqing.wxmp.config.mybatis.handler;

import com.zhengqing.common.db.config.mybatis.handler.DbJsonTypeHandler;
import com.zhengqing.wxmp.model.bo.WxMpTemplateMsgDataBO;

/**
 * <p> 自定义类型处理器 </p>
 *
 * @author zhengqingya
 * @description 模板数据
 * @date 2022/6/6 11:26
 */
public class WxMpTemplateMsgDataTypeHandler extends DbJsonTypeHandler {

    public WxMpTemplateMsgDataTypeHandler(Class<?> type) {
        super(type, WxMpTemplateMsgDataBO.class);
    }

}