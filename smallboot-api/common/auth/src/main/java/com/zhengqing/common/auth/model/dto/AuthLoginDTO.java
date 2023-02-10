package com.zhengqing.common.auth.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 登录参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginDTO {

    @NotBlank(message = "账号不能为空!")
    @ApiModelProperty(value = "账号", required = true, example = "test")
    private String username;

    @NotBlank(message = "密码不能为空!")
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

}
