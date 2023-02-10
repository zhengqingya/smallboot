package com.zhengqing.common.auth.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class AuthLoginVO {

    @ApiModelProperty("认证请求头名")
    private String tokenName;

    @ApiModelProperty("认证值")
    private String tokenValue;


}
