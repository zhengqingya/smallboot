package com.zhengqing.mall.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-商品-预售状态-枚举类 </p>
 *
 * @author zhengqingya
 * @description 1：预售前 2：预售中 3：预售后
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum PmsSpuPresellStatusEnum {

    /**
     * 预售前
     */
    BEFORE((byte) 1, "预售前"),
    /**
     * 预售中
     */
    ING((byte) 2, "预售中"),
    /**
     * 预售后
     */
    AFTER((byte) 3, "预售后");

    /**
     * 预售状态
     */
    private final Byte status;
    /**
     * 描述
     */
    private final String desc;

    private static final List<PmsSpuPresellStatusEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(PmsSpuPresellStatusEnum.values()));
    }

    /**
     * 根据指定商品预售状态查找相应枚举
     */
    public static PmsSpuPresellStatusEnum getEnum(Byte type) {
        for (PmsSpuPresellStatusEnum itemEnum : LIST) {
            if (itemEnum.getStatus().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定商品预售状态！");
    }

}
