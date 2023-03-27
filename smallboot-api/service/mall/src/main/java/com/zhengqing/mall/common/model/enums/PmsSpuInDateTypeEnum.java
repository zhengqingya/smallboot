package com.zhengqing.mall.common.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-有效期类型-枚举类 </p>
 *
 * @author zhengqingya
 * @description 1->永久有效,2->购买后n日内有效
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum PmsSpuInDateTypeEnum {

    /**
     * 永久有效
     */
    FOR_EVER((byte) 1, "永久有效"),
    /**
     * 购买后n日内有效
     */
    IN_DAY((byte) 2, "购买后n日内有效");

    /**
     * 类型
     */
    private final Byte type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<PmsSpuInDateTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(PmsSpuInDateTypeEnum.values()));
    }

    /**
     * 根据指定卡有效期类型查找相应枚举
     */
    public static PmsSpuInDateTypeEnum getEnum(Byte type) {
        for (PmsSpuInDateTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到卡指定有效期类型！");
    }

}
