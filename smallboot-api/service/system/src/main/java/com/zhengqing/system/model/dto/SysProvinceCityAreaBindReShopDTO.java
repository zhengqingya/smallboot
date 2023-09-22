package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.parameter.HandleParam;
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
 * <p>系统管理-省市区-绑定或解除关联店铺-请求参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/14 11:38
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-省市区-绑定或解除关联店铺-请求参数")
public class SysProvinceCityAreaBindReShopDTO extends BaseDTO implements HandleParam {

    @NotNull
    @ApiModelProperty("是否存在门店（1:是 0:否）")
    private Boolean isShop;

    @NotBlank
    @ApiModelProperty("省名称")
    private String provinceName;

    @NotBlank
    @ApiModelProperty("市名称")
    private String cityName;

    @NotBlank
    @ApiModelProperty("区名称")
    private String areaName;

    @Override
    public void handleParam() {

    }
}
