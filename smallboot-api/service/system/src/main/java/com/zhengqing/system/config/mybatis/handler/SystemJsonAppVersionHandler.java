package com.zhengqing.system.config.mybatis.handler;

import com.zhengqing.common.db.config.mybatis.handler.DbJsonTypeHandler;
import com.zhengqing.common.sdk.douyin.service.model.vo.DyServiceVersionVO;

/**
 * <p> 自定义类型处理器 </p>
 *
 * @author zhengqingya
 * @description 小程序版本信息
 * @date 2022/6/6 11:26
 */
public class SystemJsonAppVersionHandler extends DbJsonTypeHandler {

    public SystemJsonAppVersionHandler(Class<?> type) {
        super(type, DyServiceVersionVO.Data.class);
    }

}