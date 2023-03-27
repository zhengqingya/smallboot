package com.zhengqing.mall.mini.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> mini-商城-商品sku-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/14 14:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("mini-商城-商品sku-提交参数")
public class MiniPmsSpuBuySkuDTO {

    @NotNull(message = "商品id不能为空！")
    @ApiModelProperty(value = "商品id", required = true, example = "1")
    private Long spuId;

    @NotBlank(message = "商品sku-id不能为空")
    @ApiModelProperty(value = "商品sku-id", required = true, example = "1")
    private String skuId;

    @NotNull(message = "商品数量不能为空！")
    @ApiModelProperty(value = "商品数量", required = true, example = "1")
    private Integer num;

    @NotNull(message = "商品价格不能为空！")
    @ApiModelProperty(value = "商品价格(单位:分)", required = true, example = "1")
    private Integer price;

}
