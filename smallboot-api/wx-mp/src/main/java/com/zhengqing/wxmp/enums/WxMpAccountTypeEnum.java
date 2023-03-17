package com.zhengqing.wxmp.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 微信公众号-账号类型 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/13 20:25
 */
@Getter
@AllArgsConstructor
public enum WxMpAccountTypeEnum {

    测试号((byte) 1, "测试号"),
    服务号((byte) 2, "服务号"),
    订阅号((byte) 3, "订阅号");

    private final Byte type;
    private final String desc;

    private static final List<WxMpAccountTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(WxMpAccountTypeEnum.values()));
    }

    public static WxMpAccountTypeEnum getEnum(Byte type) {
        for (WxMpAccountTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的微信公众号账号类型！");
    }

}
