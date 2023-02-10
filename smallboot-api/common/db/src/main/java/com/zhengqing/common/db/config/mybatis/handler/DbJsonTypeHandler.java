package com.zhengqing.common.db.config.mybatis.handler;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * <p> 自定义 TypeHandler 类型处理器 </p>
 *
 * @author zhengqingya
 * @description json类型转对象
 * @date 2022/6/6 11:25
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes({Object.class})
public class DbJsonTypeHandler extends AbstractJsonTypeHandler<Object> {

    private final Class<?> clazz;

    public DbJsonTypeHandler(Class<?> type, Class<?> innerType) {
        Assert.notNull(type, "Type argument cannot be null ...");
        this.clazz = innerType;
    }

    @Override
    protected Object parse(String json) {
        if (JSONUtil.isJsonArray(json)) {
            return JSONUtil.toList(JSONUtil.parseArray(json), this.clazz);
        } else {
            return JSONUtil.toBean(json, this.clazz);
        }
    }

    @Override
    protected String toJson(Object obj) {
        return JSONUtil.toJsonStr(obj);
    }

}
