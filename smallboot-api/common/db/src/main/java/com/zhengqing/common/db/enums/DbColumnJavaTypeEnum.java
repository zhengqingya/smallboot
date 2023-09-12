package com.zhengqing.common.db.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * java字段类型枚举类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/15 22:04
 */
@Getter
@AllArgsConstructor
public enum DbColumnJavaTypeEnum {

    字符串类型_varchar("varchar", "String"),
    字符串类型_text("text", "String"),
    数字类型_tinyint("tinyint", "Byte"),
    数字类型_int("int", "Integer"),
    数字类型_long("bigint", "Long"),
    布尔类型_bit("bit", "Boolean"),
    时间类型("datetime", "Date");

    /**
     * 数据库字段类型
     */
    private final String columnTypeDb;
    /**
     * java字段类型
     */
    private final String columnTypeJava;

    private static final List<DbColumnJavaTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(DbColumnJavaTypeEnum.values()));
    }

    /**
     * 根据数据库字段类型匹配java字段类型
     *
     * @param columnTypeDb: 数据库字段类型
     * @return 枚举信息
     * @author zhengqingya
     * @date 2020/11/15 22:09
     */
    public static DbColumnJavaTypeEnum getEnum(String columnTypeDb) {
        // 截取数据库字段类型 ex: `int(11)` -> `int`
        if (columnTypeDb.contains("(")) {
            columnTypeDb = columnTypeDb.substring(0, columnTypeDb.indexOf("("));
        }
        for (DbColumnJavaTypeEnum itemEnum : LIST) {
            if (itemEnum.getColumnTypeDb().equalsIgnoreCase(columnTypeDb)) {
                return itemEnum;
            }
        }
        return 字符串类型_varchar;
    }

}
