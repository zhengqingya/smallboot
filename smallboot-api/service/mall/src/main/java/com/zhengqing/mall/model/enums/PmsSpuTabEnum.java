package com.zhengqing.mall.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-商品上下架-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum PmsSpuTabEnum {

    /**
     * 全部
     */
    ALL((byte) -1, 1, "全部"),
    /**
     * 上架
     */
    ON((byte) 1, 2, "上架"),
    /**
     * 下架
     */
    OFF((byte) 0, 3, "下架");

    /**
     * 值
     */
    private final Byte value;
    /**
     * tab条件查询时的排序值
     */
    private final Integer tabSort;
    /**
     * 名称
     */
    private final String name;

    public static final List<PmsSpuTabEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(PmsSpuTabEnum.values()));
    }

    /**
     * 根据指定商品tab查找相应枚举
     */
    public static PmsSpuTabEnum getEnum(Byte value) {
        for (PmsSpuTabEnum itemEnum : LIST) {
            if (itemEnum.getValue().equals(value)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定商品tab类型！");
    }

}
