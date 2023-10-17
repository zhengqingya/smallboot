package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * <p> 小程序二维码-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("小程序二维码-请求参数")
public class SysAppQrcodeDTO extends BaseDTO {

    @NotBlank(message = "小程序appid未配置！")
    @ApiModelProperty("appid")
    private String appId;

    @NotBlank(message = "版本类型不能为空！")
    @ApiModelProperty("current（线上版） 或 audit（审核版） 或 latest（测试版）")
    private String version;

    @ApiModelProperty("小程序启动参数  eg: pages/result/index?name=xxx")
    private String path;

}
