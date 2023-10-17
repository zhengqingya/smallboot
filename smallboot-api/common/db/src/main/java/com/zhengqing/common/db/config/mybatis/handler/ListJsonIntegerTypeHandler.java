package com.zhengqing.common.db.config.mybatis.handler;

/**
 * <p> 自定义类型处理器 </p>
 *
 * @author zhengqingya
 * @description eg:  @TableField(typeHandler = ListJsonIntegerTypeHandler.class)
 * 数据库json数据 [1,2,3]  =》 java的 List<Integer>
 * @date 2022/6/6 11:26
 */
public class ListJsonIntegerTypeHandler extends DbJsonTypeHandler {

    public ListJsonIntegerTypeHandler(Class<?> type) {
        super(type, Integer.class);
    }

}