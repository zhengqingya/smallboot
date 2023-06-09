package com.zhengqing.wxmp.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.wxmp.enums.WxMpUserSubscribeSceneEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p> 微信公众号-用户-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("微信公众号-用户-保存-提交参数")
public class WxMpUserSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @ApiModelProperty("AppID")
    private String appId;

    @ApiModelProperty("微信openid")
    private String openid;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String headImgUrl;

    /**
     * {@link WxMpUserSubscribeSceneEnum}
     */
    @ApiModelProperty("用户关注的渠道来源")
    private String subscribeScene;

    @ApiModelProperty("关注时间")
    private Date subscribeTime;

}
