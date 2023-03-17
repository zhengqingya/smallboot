package com.zhengqing.wxmp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import com.zhengqing.wxmp.enums.WxMpMsgAutoReplyTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  微信公众号-消息自动回复 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_wx_mp_msg_auto_reply")
@ApiModel("微信公众号-消息自动回复")
public class WxMpMsgAutoReply extends BaseEntity<WxMpMsgAutoReply> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("AppID")
    private String appId;

    @ApiModelProperty("名称")
    private String name;

    /**
     * {@link WxMpMsgAutoReplyTypeEnum}
     */
    @ApiModelProperty("类型（1：关注时回复；2：关键词回复）")
    private Byte type;

    @ApiModelProperty("关键词")
    private String matchValue;

    @ApiModelProperty("是否精确匹配（0：否；1：是）")
    private Byte isExactMatch;

    @ApiModelProperty("回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）")
    private String replyType;

    @ApiModelProperty("回复消息内容")
    private String replyContent;

}
