package com.zhengqing.wxmp.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.wxmp.enums.WxMpAutoReplyMsgTypeEnum;
import com.zhengqing.wxmp.enums.WxMpAutoReplyTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>微信公众号-消息自动回复-分页列表-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("微信公众号-消息自动回复-分页列表-响应参数")
public class WxMpMsgAutoReplyPageVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("AppID")
    private String appId;

    @ApiModelProperty("名称")
    private String name;

    /**
     * {@link WxMpAutoReplyTypeEnum}
     */
    @ApiModelProperty("类型（1：关注时回复；2：关键词回复）")
    private Byte type;

    @ApiModelProperty("类型值")
    private String typeName;

    @ApiModelProperty("关键词")
    private String matchValue;

    @ApiModelProperty("是否精确匹配（false：否；true：是）")
    private Boolean isExactMatch;

    /**
     * {@link WxMpAutoReplyMsgTypeEnum}
     */
    @ApiModelProperty("回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）")
    private String replyType;

    @ApiModelProperty("回复消息类型值")
    private String replyTypeName;

    @ApiModelProperty("回复消息内容")
    private String replyContent;

    public void handleData() {
        this.typeName = WxMpAutoReplyTypeEnum.getEnum(this.type).getDesc();
        this.replyTypeName = WxMpAutoReplyMsgTypeEnum.getEnum(this.replyType).getDesc();
    }

}
