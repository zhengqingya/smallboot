package com.zhengqing.wxmp.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.wxmp.enums.WxMpAutoReplyTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 微信公众号-消息自动回复-提交参数 </p>
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
public class WxMpReplyMsgDTO extends BaseDTO {

    @ApiModelProperty("AppID")
    private String appId;

    @ApiModelProperty("发送用户")
    private String fromUser;

    /**
     * {@link WxMpAutoReplyTypeEnum}
     */
    @ApiModelProperty("类型（1：关注时回复；2：关键词回复）")
    private Byte type;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("是否精确匹配（false：否；true：是）")
    private Boolean isExactMatch;

}
