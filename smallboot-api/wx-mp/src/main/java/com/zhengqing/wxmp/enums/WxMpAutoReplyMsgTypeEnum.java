package com.zhengqing.wxmp.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 微信公众号-自动回复消息类型 </p>
 *
 * @author zhengqingya
 * @description {@link me.chanjar.weixin.common.api.WxConsts.KefuMsgType}
 * @date 2021/1/13 20:25
 */
@Getter
@AllArgsConstructor
public enum WxMpAutoReplyMsgTypeEnum {

    文本("text", "文本"),
    图片("image", "图片"),
    语音("voice", "语音"),
    视频("video", "视频"),
    音乐("music", "音乐"),
    图文("news", "图文");

    private final String type;
    private final String desc;

    private static final List<WxMpAutoReplyMsgTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(WxMpAutoReplyMsgTypeEnum.values()));
    }

    public static WxMpAutoReplyMsgTypeEnum getEnum(String type) {
        for (WxMpAutoReplyMsgTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的微信公众号自动回复消息类型！");
    }

}
