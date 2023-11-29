package com.zhengqing.common.db.config.mybatis.handler;

/**
 * <p> 自定义类型处理器 </p>
 *
 * @author zhengqingya
 * @description eg:  @TableField(typeHandler = ListJsonStringTypeHandler.class)
 * 数据库json数据 ['1', '2' ,'3']  =》 java的 List<String>
 * @date 2022/6/6 11:26
 */
public class ListJsonStringTypeHandler extends DbJsonTypeHandler {

    public ListJsonStringTypeHandler(Class<?> type) {
        super(type, String.class);
    }

}