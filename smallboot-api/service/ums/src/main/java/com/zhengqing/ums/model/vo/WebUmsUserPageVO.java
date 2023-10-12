package com.zhengqing.ums.model.vo;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.enums.UserSexEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p>
 * 用户信息-分页列表 响应参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 10:48
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class WebUmsUserPageVO extends BaseDTO {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("openid")
    private String openid;

    @ApiModelProperty("unionid")
    private String unionid;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("手机号码")
    private String phone;

    /**
     * {@link UserSexEnum}
     */
    @ApiModelProperty("性别")
    private Byte sex;

    @ApiModelProperty("头像")
    private String avatarUrl;

    @ApiModelProperty("创建时间")
    private Date createTime;

}