package com.zhengqing.ums.model.dto;

import cn.hutool.core.lang.Assert;
import com.zhengqing.common.base.exception.ParameterException;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.parameter.CheckParam;
import com.zhengqing.common.core.custom.parameter.HandleParam;
import com.zhengqing.ums.enums.MiniTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class UmsUserLoginDTO extends BaseDTO implements HandleParam, CheckParam {

    /**
     * {@link com.zhengqing.ums.enums.MiniTypeEnum}
     */
    @NotNull
    @ApiModelProperty("小程序类型")
    private Integer type;

    @ApiModelProperty("小程序appid")
    private String appId;

    @ApiModelProperty("小程序secret")
    private String appSecret;

//    @ApiModelProperty("第三方小程序应用 appid")
//    private String component_appid;
//
//    @ApiModelProperty("第三方小程序应用 appsecret")
//    private String component_appsecret;

    @NotBlank
    @ApiModelProperty("登录凭证")
    private String code;

    @ApiModelProperty("匿名登录凭证")
    private String anonymousCode;

    @ApiModelProperty("用户信息")
    private UmsUserInfoDTO userInfo;

    @ApiModelProperty("是否本地测试登录(仅测试使用)")
    private Boolean isLocalLogin;

    @Override
    public void handleParam() {
        if (this.isLocalLogin == null) {
            this.isLocalLogin = false;
        }
    }

    @Override
    public void checkParam() throws ParameterException {
        if (MiniTypeEnum.抖音小程序.getType().equals(this.type)) {
            Assert.notBlank(this.appId, "AppId不能为空！");
            Assert.notBlank(this.appSecret, "AppSecret不能为空！");
        }
    }
}