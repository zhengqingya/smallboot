package com.zhengqing.wxmp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import com.zhengqing.wxmp.enums.WxMpUserSubscribeSceneEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p>  微信公众号-用户 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_wx_mp_user")
@ApiModel("微信公众号-用户")
public class WxMpUser extends BaseEntity<WxMpUser> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
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
