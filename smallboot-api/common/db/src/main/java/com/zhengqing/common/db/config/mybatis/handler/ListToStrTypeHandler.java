package com.zhengqing.common.db.config.mybatis.handler;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * <p> 自定义 TypeHandler 类型处理器 </p>
 *
 * @author zhengqingya
 * @description list类型转varchar（逗号分隔的字符串）的类型处理器
 * ex      list [1,2,3]  ==》 varchar 1,2,3
 * @date 2022/6/6 11:25
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes({List.class})
public class ListToStrTypeHandler implements TypeHandler<List<String>> {

    @Override
    public void setParameter(PreparedStatement ps, int columnIndex, List<String> columnValueList, JdbcType jdbcType) throws SQLException {
        String columnValue = Joiner.on(",").join(columnValueList);
        ps.setString(columnIndex, columnValue);
    }

    @Override
    public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        if (StringUtils.isBlank(columnValue)) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(columnValue.split(","));
    }

    @Override
    public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }

}
