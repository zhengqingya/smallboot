package com.zhengqing.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 系统管理-用户基础信息表查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 10:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("系统管理-用户基础信息表查询参数")
public class SysUserUpdatePasswordDTO {

    @NotNull(message = "用户ID不能为空！")
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

//    @Length(min = 6, message = "密码最少6位数!")
//    @NotBlank(message = "旧密码不能为空！")
//    @ApiModelProperty(value = "密码")
//    private String password;

    @Length(min = 6, message = "密码最少6位数!")
    @NotBlank(message = "新密码不能为空！")
    @ApiModelProperty("新密码")
    private String newPassword;

}
