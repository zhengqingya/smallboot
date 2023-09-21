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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
public class SysProvinceCityAreaTreeDTO extends BaseDTO implements HandleParam {

    @ApiModelProperty("是否存在门店（1:是 0:否）")
    private Boolean isShop;

    /**
     * {@link com.zhengqing.system.enums.SysProvinceCityAreaTypeEnum}
     */
    @Max(3)
    @Min(1)
    @ApiModelProperty("类型（1:省 2:市 3:区） -- 用于查询第几级下的数据")
    private Integer type;

    @Override
    public void handleParam() {
        
    }
}
