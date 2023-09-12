package com.zhengqing.common.db.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 数据源类型枚举类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/2 15:52
 */
@Getter
@AllArgsConstructor
public enum DataSourceTypeEnum {

    /**
     * mysql8: com.mysql.cj.jdbc.Driver
     * mysql5.7: com.mysql.jdbc.Driver
     */
    MySQL("1", "com.mysql.cj.jdbc.Driver", "MySQL"),
    Oracle("2", "oracle.jdbc.driver.OracleDriver", "Oracle");

    private final String type;
    private final String driverClassName;
    private final String desc;

    private static final List<DataSourceTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(DataSourceTypeEnum.values()));
    }

    public static DataSourceTypeEnum getEnum(String type) {
        for (DataSourceTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到正确的数据源类型！");
    }

}
