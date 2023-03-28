package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> 商城-虚拟销量-保存参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/20 16:11
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-虚拟销量-保存参数")
public class WebPmsSpuEditVirtualUseStockDTO extends BaseDTO {

    @NotBlank(message = "商品sku-id不能为空")
    @ApiModelProperty(value = "商品sku-id", required = true, example = "1")
    private String id;

    @NotNull(message = "销量值不能为空")
    @Min(value = 0, message = "最小为0")
    @ApiModelProperty(value = "虚拟-已用库存(虚拟销量)", required = true, example = "1")
    private Integer virtualUseStock;

}
