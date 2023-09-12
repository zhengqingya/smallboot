package com.zhengqing.common.db.util;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.db.enums.DataSourceTypeEnum;
import com.zhengqing.common.db.enums.DbOperateSqlEnum;
import com.zhengqing.common.db.model.vo.DbTableColumnListVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * <p> JDBC工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/7/22 20:48
 */
@Slf4j
public class MyJdbcUtil {

    /**
     * 根据数据库连接信息+库名+表名查询具体表字段信息
     *
     * @param dataSourceTypeEnum 数据源类型枚举
     * @param ipAddress          指向要访问的数据库ip地址
     * @param port               端口
     * @param username           用户名
     * @param password           密码
     * @param dbName             库名
     * @param tableName          表名
     * @return 表字段列表
     * @author zhengqingya
     * @date 2021/8/21 5:15 下午
     */
    @SneakyThrows(Exception.class)
    public DbTableColumnListVO getAllColumnsByDbInfo(DataSourceTypeEnum dataSourceTypeEnum, String ipAddress, String port, String username, String password, String dbName, String tableName) {
        // 1、连接数据库
        Connection conn = this.getConnectionBase(dataSourceTypeEnum, ipAddress, port, username, password, dbName);

        // 返回结果声明
        DbTableColumnListVO result = new DbTableColumnListVO();
        result.setDataSourceId(null);
        result.setDbName(dbName);
        result.setTableName(tableName);
        List<DbTableColumnListVO.ColumnInfo> columnInfoList = Lists.newArrayList();

        // 2、获取sql预编译对象
        Statement stmt = conn.createStatement();

        // 3、执行查询
        // 3.1、查看指定库下指定表注释
        String selectTableCommentSql = String.format(DbOperateSqlEnum.查看指定库下指定表注释.getSql(), dbName, tableName);
        ResultSet rsSelectTableComment = stmt.executeQuery(selectTableCommentSql);
        // 3.1.1、展开结果集数据库
        while (rsSelectTableComment.next()) {
            result.setTableComment(rsSelectTableComment.getString("tableComment"));
        }

        // 3.2、查看指定库和表下所有字段信息
        String sql = String.format(DbOperateSqlEnum.查看指定库和表下所有字段信息.getSql(), dbName, tableName);
        ResultSet rs = stmt.executeQuery(sql);
        // 3.2.1、展开结果集数据库
        while (rs.next()) {
            // BeanHandler方式获取的数据不完整
            // BeanHandler<StDbTableColumnVO.ColumnInfo> bh = new BeanHandler<>(StDbTableColumnVO.ColumnInfo.class);
            // StDbTableColumnVO.ColumnInfo columnInfo = bh.handle(rs);
            // 封装表字段信息
            DbTableColumnListVO.ColumnInfo columnInfo = new DbTableColumnListVO.ColumnInfo();
            columnInfo.setColumnName(rs.getString("columnName"));
            columnInfo.setColumnComment(rs.getString("columnComment"));
            String columnType = rs.getString("columnType");
            if (!columnType.contains("(")) {
                columnType = columnType + "(" + 0 + ")";
            }
            columnInfo.setColumnType(columnType);
            columnInfo.setIfNullAble(Boolean.parseBoolean(rs.getString("ifNullAble")));
            columnInfo.setIfPrimaryKey(Boolean.parseBoolean(rs.getString("ifPrimaryKey")));
            columnInfo.setIfAutoIncrement(Boolean.parseBoolean(rs.getString("ifAutoIncrement")));
            columnInfo.setColumnDefaultValue(rs.getString("columnDefaultValue"));
            columnInfoList.add(columnInfo);
        }

        // 4、释放资源(注意：关闭资源顺序 -> 先打开后关闭)
        rsSelectTableComment.close();
        rs.close();
        stmt.close();
        conn.close();
        result.setColumnInfoList(columnInfoList);
        return result;
    }


    /**
     * 连接数据库
     *
     * @param dataSourceTypeEnum 数据源类型枚举
     * @param ipAddress          指向要访问的数据库ip地址
     * @param port               端口
     * @param username           用户名
     * @param password           密码
     * @param dbName             库名
     * @return java.sql.Connection
     * @author zhengqingya
     * @date 2021/8/21 5:11 下午
     */
    private Connection getConnectionBase(DataSourceTypeEnum dataSourceTypeEnum, String ipAddress, String port, String username, String password, String dbName) {
        try {
            if (StringUtils.isBlank(dbName)) {
                dbName = "mysql";
            }
            // 1、创建用于连接数据库的Connection对象
            Connection con = null;
            // 2、加载JDBC驱动
            Class.forName(dataSourceTypeEnum.getDriverClassName());
            // 3、根据数据库类型【mysql or oracle】，获取连接，与数据库建立连接
            switch (dataSourceTypeEnum) {
                case MySQL:
                    con = DriverManager.getConnection(
                            String.format(DbOperateSqlEnum.连接MySQL数据库.getSql(), ipAddress, port, dbName), username, password);
                    break;
                case Oracle:
                    con = DriverManager.getConnection(
                            String.format(DbOperateSqlEnum.连接Oracle数据库.getSql(), ipAddress, port), username, password);
                    break;
                default:
                    break;
            }
            // 4、返回所建立的数据库连接信息
            return con;
        } catch (Exception e) {
            throw new MyException("数据库连接失败：" + e.getMessage());
        }
    }

}
