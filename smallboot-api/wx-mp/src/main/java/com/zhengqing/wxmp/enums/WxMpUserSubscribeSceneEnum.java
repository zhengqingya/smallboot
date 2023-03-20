package com.zhengqing.wxmp.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 微信公众号-用户关注的渠道来源 </p>
 *
 * @author zhengqingya
 * @description {@link me.chanjar.weixin.mp.bean.result.WxMpUser}
 * @date 2021/1/13 20:25
 */
@Getter
@AllArgsConstructor
public enum WxMpUserSubscribeSceneEnum {

    ADD_SCENE_SEARCH("ADD_SCENE_SEARCH", "公众号搜索"),
    ADD_SCENE_ACCOUNT_MIGRATION("ADD_SCENE_ACCOUNT_MIGRATION", "公众号迁移"),
    ADD_SCENE_PROFILE_CARD("ADD_SCENE_PROFILE_CARD", "名片分享"),
    ADD_SCENE_QR_CODE("ADD_SCENE_QR_CODE", "扫描二维码"),
    ADD_SCENE_PROFILE_LINK("ADD_SCENE_PROFILE_LINK", "图文页内名称点击"),
    ADD_SCENE_PROFILE_ITEM("ADD_SCENE_PROFILE_ITEM", "图文页右上角菜单"),
    ADD_SCENE_PAID("ADD_SCENE_PAID", "支付后关注"),
    ADD_SCENE_WECHAT_ADVERTISEMENT("ADD_SCENE_WECHAT_ADVERTISEMENT", "微信广告"),
    ADD_SCENE_OTHERS("ADD_SCENE_OTHERS", "其他");

    private final String type;
    private final String desc;

    private static final List<WxMpUserSubscribeSceneEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(WxMpUserSubscribeSceneEnum.values()));
    }

    public static WxMpUserSubscribeSceneEnum getEnum(String type) {
        for (WxMpUserSubscribeSceneEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的微信公众号用户关注的渠道来源！");
    }

}
