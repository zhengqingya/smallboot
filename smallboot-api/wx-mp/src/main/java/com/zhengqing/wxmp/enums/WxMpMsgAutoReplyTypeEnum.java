package com.zhengqing.wxmp.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 微信公众号-消息自动回复类型 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/13 20:25
 */
@Getter
@AllArgsConstructor
public enum WxMpMsgAutoReplyTypeEnum {

    关注时回复((byte) 1, "关注时回复"),
    关键词回复((byte) 2, "关键词回复");

    private final Byte type;
    private final String desc;

    private static final List<WxMpMsgAutoReplyTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(WxMpMsgAutoReplyTypeEnum.values()));
    }

    public static WxMpMsgAutoReplyTypeEnum getEnum(Byte type) {
        for (WxMpMsgAutoReplyTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的微信公众号消息自动回复类型！");
    }

}
