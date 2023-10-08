package com.zhengqing.common.db.config.mybatis.handler;

/**
 * <p> 自定义类型处理器 </p>
 *
 * @author zhengqingya
 * @description eg:  @TableField(typeHandler = ListIntegerTypeHandler.class)
 * @date 2022/6/6 11:26
 */
public class ListIntegerTypeHandler extends DbJsonTypeHandler {

    public ListIntegerTypeHandler(Class<?> type) {
        super(type, Integer.class);
    }

}