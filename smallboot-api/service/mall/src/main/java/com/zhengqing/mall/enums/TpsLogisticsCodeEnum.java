package com.zhengqing.mall.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 物流公司-对应编码-枚举 </p>
 *
 * @author zhengqingya
 * @description 申通 -> STO
 * 中通 -> ZTO
 * 圆通 -> YTO
 * 韵达 -> YD
 * 顺丰 -> SF
 * 京东 -> JD
 * @date 2021/10/26 10:12
 */
@Getter
@AllArgsConstructor
public enum TpsLogisticsCodeEnum {

    /**
     * 申通
     */
    STO("STO", "申通"),
    /**
     * 中通
     */
    ZTO("ZTO", "中通"),
    /**
     * 圆通
     */
    YTO("YTO", "圆通"),
    /**
     * 韵达
     */
    YD("YD", "韵达"),
    /**
     * 顺丰
     */
    SF("SF", "顺丰"),
    /**
     * 京东
     */
    JD("JD", "京东");

    /**
     * 物流编码
     */
    private final String code;
    /**
     * 物流名
     */
    private final String name;


    private static final List<TpsLogisticsCodeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(TpsLogisticsCodeEnum.values()));
    }

    /**
     * 根据值查找相应枚举
     */
    public static TpsLogisticsCodeEnum getEnumByName(String name) {
        for (TpsLogisticsCodeEnum itemEnum : LIST) {
            if (itemEnum.getName().equals(name)) {
                return itemEnum;
            }
        }
        throw new MyException("暂无此物流编码信息，请联系系统管理员！");
    }

    /**
     * 根据值查找相应枚举
     */
    public static TpsLogisticsCodeEnum getEnumByCode(String code) {
        for (TpsLogisticsCodeEnum itemEnum : LIST) {
            if (itemEnum.getCode().equals(code)) {
                return itemEnum;
            }
        }
        throw new MyException("暂无此物流编码信息，请联系系统管理员！");
    }

}
