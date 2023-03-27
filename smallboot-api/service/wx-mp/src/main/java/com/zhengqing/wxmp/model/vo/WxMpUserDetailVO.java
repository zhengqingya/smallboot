package com.zhengqing.wxmp.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.wxmp.enums.WxMpUserSubscribeSceneEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p>微信公众号-用户-详情-响应参数</p>
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
@ApiModel("微信公众号-用户-详情-响应参数")
public class WxMpUserDetailVO extends BaseVO {

    @ApiModelProperty("主键ID")
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

    public void handleData() {

    }

}
