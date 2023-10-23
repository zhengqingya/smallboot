package com.zhengqing.common.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 扩展Mapper基类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/8/3 18:33
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量保存 -- MySQL
     * https://gitee.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-extension/src/main/java/com/baomidou/mybatisplus/extension/injector/methods/InsertBatchSomeColumn.java
     *
     * @param entityList 保存实体类
     * @return 操作数
     * @author zhengqingya
     * @date 2020/8/3 18:41
     */
    int insertBatchSomeColumn(List<T> entityList);

    /**
     * 根据 ID 更新固定的那几个字段(但是不包含逻辑删除)
     * https://gitee.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-extension/src/main/java/com/baomidou/mybatisplus/extension/injector/methods/AlwaysUpdateSomeColumnById.java
     *
     * @param entity 实体类
     * @return 操作数
     * @author zhengqingya
     * @date 2020/8/3 18:41
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);


    /**
     * 根据指定条件，查询总记录数 【注：${key} 不能使用 #{key}】
     *
     * @param tableName 表名
     * @param columnMap 查询校验字段组值
     * @return 总记录数
     * @author zhengqingya
     * @date 2020/8/3 18:41
     */
    @Select({"<script>",
            "SELECT", "COUNT(*)", "FROM ${table_name}",
            "<where>",
            " AND is_deleted = 0 ",
            "<if test=\"columnMap != null and columnMap.size() != 0\">", " AND ",
            "<foreach collection='columnMap.keys' item='key' separator=' AND '>",
            "${key} = #{columnMap[${key}]}",
            "</foreach>",
            "</if>",
            "</where>",
            "</script>"})
    Integer selectCountBySomeColumn(@Param("table_name") String tableName, @Param("columnMap") Map<Object, Object> columnMap);

    /**
     * 根据指定条件，查询全部记录 【注：${key} 不能使用 #{key}】
     *
     * @param tableName 表名
     * @param columnMap 查询校验字段组值
     * @return 全部记录
     * @author zhengqingya
     * @date 2020/8/3 18:41
     */
    @Select({"<script>",
            "SELECT", "*", "FROM ${table_name}",
            "<where>",
            " AND is_deleted = 0 ",
            "<if test=\"columnMap != null and columnMap.size() != 0\">", " AND ",
            "<foreach collection='columnMap.keys' item='key' separator=' AND '>",
            "${key} = #{columnMap[${key}]}",
            "</foreach>",
            "</if>",
            "</where>",
            "</script>"})
    List<Map<String, Object>> selectListBySomeColumn(@Param("table_name") String tableName, @Param("columnMap") Map<Object, Object> columnMap);

    /**
     * 执行sql
     *
     * @param sql sql
     * @return void
     * @author zhengqingya
     * @date 2022/7/22 16:46
     */
    @Update("#{sql}")
    void execSql(@Param("sql") String sql);

}
