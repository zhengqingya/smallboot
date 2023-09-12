package com.zhengqing.common.db.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 数据库sql常用操作枚举类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/6 1:44
 */
@Getter
@AllArgsConstructor
public enum DbOperateSqlEnum {

    连接MySQL数据库("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF8&useSSL=false", "连接MySQL数据库"),
    连接Oracle数据库("jdbc:oracle:thin:@%s:%s:oracle", "连接Oracle数据库"), 查看显示所有数据库("SHOW DATABASES", "查看显示所有数据库"),
    查看当前使用的数据库("SELECT DATABASE()", "查看当前使用的数据库"), 查看当前数据库使用端口("SHOW VARIABLES LIKE 'port'", "查看当前数据库使用端口"),
    查看当前数据库大小(
            "SELECT CONCAT( ROUND( SUM( data_length ) / ( 1024 * 1024 ), 2 ) + ROUND( SUM( index_length ) / ( 1024 * 1024 ), 2 ), 'MB' ) AS 'db_size' FROM TABLES",
            "查看当前数据库大小"),
    查看指定库下所有表信息(
            "SELECT TABLE_NAME tableName,ENGINE engine,TABLE_ROWS tableRows,DATA_LENGTH dataLength,AUTO_INCREMENT autoIncrement,CREATE_TIME createTime,UPDATE_TIME updateTime,TABLE_COLLATION tableCollation,TABLE_COMMENT tableComment FROM information_schema.TABLES WHERE table_schema = '%s'",
            "查看指定库下所有表信息"),
    查看指定库和表下所有字段信息(
            "SELECT COLUMN_NAME columnName, COLUMN_COMMENT columnComment, COLUMN_TYPE columnType, IF(IS_NULLABLE='YES','true','false') ifNullAble, IF(COLUMN_KEY='PRI','true','false') ifPrimaryKey, IF(EXTRA='auto_increment', 'true','false') ifAutoIncrement, COLUMN_DEFAULT columnDefaultValue FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s'",
            "查看指定库和表下所有字段信息"),
    查看指定库下指定表注释(
            "SELECT TABLE_COMMENT tableComment FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s'",
            "查看指定库下指定表注释"),
    修改指定库下指定表注释("ALTER TABLE %s.%s COMMENT '%s'", "修改指定库下指定表注释"),
    修改指定库下指定表字段信息("ALTER TABLE %s.%s MODIFY `%s` %s %s %s COMMENT '%s'", "修改指定库下指定表字段信息"),
    修改指定库下指定表主键ID("ALTER TABLE %s.%s DROP PRIMARY KEY, ADD PRIMARY KEY ( %s );", "修改指定库下指定表主键ID"),
    修改指定库下指定表指定字段自增长("ALTER TABLE %s.%s MODIFY %s INT AUTO_INCREMENT;", "修改指定库下指定表指定字段自增长");

    private final String sql;
    private final String desc;

}
