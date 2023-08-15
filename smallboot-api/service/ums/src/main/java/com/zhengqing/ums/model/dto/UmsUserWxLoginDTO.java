package com.zhengqing.ums.model.dto;

import com.zhengqing.common.base.exception.ParameterException;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.parameter.CheckParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 用户信息-微信小程序登录参数
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
public class UmsUserWxLoginDTO extends BaseDTO implements CheckParam {

//    @NotBlank
//    @ApiModelProperty("消息密文")
//    private String encryptedData;

//    @NotBlank
//    @ApiModelProperty("加密算法的初始向量")
//    private String iv;

    @NotBlank
    @ApiModelProperty("动态令牌")
    private String code;

//    @NotNull
//    @ApiModelProperty("敏感信息加密数据")
//    private String rawData;

//    @NotNull
//    @ApiModelProperty("签名信息")
//    private String signature;

    @ApiModelProperty("用户信息")
    private UmsUserInfoDTO userInfo;

    @ApiModelProperty("是否本地测试登录(仅测试使用)")
    private Boolean isLocalLogin;

    @Override
    public void checkParam() throws ParameterException {
        if (this.isLocalLogin == null) {
            this.isLocalLogin = false;
        }
    }

}