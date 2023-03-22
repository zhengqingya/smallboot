package com.zhengqing.wxmp.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.chanjar.weixin.common.api.WxConsts;

import javax.validation.constraints.NotBlank;

/**
 * <p> 微信公众号-素材管理-保存-请求参数 </p>
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
@ApiModel("微信公众号-素材管理-保存-请求参数")
public class WxMpMaterialSaveDTO extends BaseDTO {

    @NotBlank(message = "AppID不能为空！")
    @ApiModelProperty("AppID")
    private String appId;

    /**
     * {@link WxConsts.MaterialType}
     */
    @NotBlank(message = "素材类型不能为空！")
    @ApiModelProperty("素材类型")
    private String mediaType;

    @NotBlank(message = "素材名称不能为空！")
    @ApiModelProperty("素材名称")
    private String name;

//    @ApiModelProperty("素材")
//    private MultipartFile file;

}
