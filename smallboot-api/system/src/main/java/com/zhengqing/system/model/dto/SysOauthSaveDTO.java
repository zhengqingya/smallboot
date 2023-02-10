package com.zhengqing.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 系统管理 - 用户三方授权表提交参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 22:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("系统管理 - 用户三方授权表提交参数")
public class SysOauthSaveDTO {

    @NotNull(message = "用户id不能为空！")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @NotNull(message = "第三方授权类型不能为空！")
    @ApiModelProperty(value = "第三方授权类型")
    private Integer oauthType;

    @NotBlank(message = "三方id不能为空！")
    @ApiModelProperty(value = "三方id")
    private String openId;

    // @ApiModelProperty(value = "三方授权用户信息")
    // private ThirdpartOauthUserInfoBO oauthUserInfo;

}
