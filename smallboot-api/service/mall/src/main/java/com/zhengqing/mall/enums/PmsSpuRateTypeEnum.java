package com.zhengqing.mall.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-评价分类-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/25 10:03 下午
 */
@Getter
@AllArgsConstructor
public enum PmsSpuRateTypeEnum {

    /**
     * 好评- 总星星数>=9
     */
    GOOD((byte) 1, "好评"),
    /**
     * 差评- 总星星数=3
     */
    BAD((byte) 2, "差评"),
    /**
     * 一般- 3<总星星数<9
     */
    GENERAL((byte) 3, "一般");

    /**
     * 类型
     */
    private final Byte type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<PmsSpuRateTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(PmsSpuRateTypeEnum.values()));
    }

    /**
     * 根据指定评价分类查找相应枚举
     */
    public static PmsSpuRateTypeEnum getEnum(Byte type) {
        for (PmsSpuRateTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定评价分类！");
    }

}
