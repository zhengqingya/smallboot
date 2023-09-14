package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>系统管理-省市区-树-请求参数</p>
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
@ApiModel("系统管理-省市区-树-请求参数")
public class SysProvinceCityAreaTreeDTO extends BaseDTO {

    @ApiModelProperty("是否存在门店（1:是 0:否）")
    private Boolean isShop;


}
