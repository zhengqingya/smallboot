package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p> 系统管理-小程序服务商平台配置-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/20 16:03
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-小程序服务商平台配置-保存-提交参数")
public class SysAppServiceConfigSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("第三方小程序应用appid")
    private String componentAppId;

    @ApiModelProperty("第三方小程序应用appsecret")
    private String componentAppSecret;

    @ApiModelProperty("消息验证TOKEN")
    private String tpToken;

    @ApiModelProperty("消息加密解密KEY")
    private String encodingAesKey;


}
