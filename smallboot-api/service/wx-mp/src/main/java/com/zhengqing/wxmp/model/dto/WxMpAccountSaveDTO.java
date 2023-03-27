package com.zhengqing.wxmp.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.wxmp.enums.WxMpAccountTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> 微信公众号-账号-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("微信公众号-账号-保存-提交参数")
public class WxMpAccountSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @NotBlank(message = "名称不能为空！")
    @ApiModelProperty("名称")
    private String name;

    /**
     * {@link WxMpAccountTypeEnum}
     */
    @NotNull(message = "账号类型不能为空（1：测试号；2：服务号；3：订阅号）！")
    @ApiModelProperty("账号类型（1：测试号；2：服务号；3：订阅号）")
    private Byte type;

    @NotBlank(message = "公众号ID不能为空！")
    @ApiModelProperty("公众号ID")
    private String account;

    @NotBlank(message = "AppID不能为空！")
    @ApiModelProperty("AppID")
    private String appId;

    @NotBlank(message = "密钥不能为空！")
    @ApiModelProperty("密钥")
    private String appSecret;

    @NotBlank(message = "服务器地址不能为空！")
    @ApiModelProperty("服务器地址")
    private String url;

    @NotBlank(message = "令牌不能为空！")
    @ApiModelProperty("令牌")
    private String token;

    @NotBlank(message = "消息加密密钥不能为空！")
    @ApiModelProperty("消息加密密钥")
    private String aesKey;

    @NotBlank(message = "二维码地址不能为空！")
    @ApiModelProperty("二维码地址")
    private String qrCodeUrl;


}
