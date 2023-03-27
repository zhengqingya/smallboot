package com.zhengqing.mall.common.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> 商城-商品sku-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/26 15:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商城-商品sku-提交参数")
public class PmsSpuBuyNumInfoBO {

    @NotBlank(message = "商品sku-id不能为空")
    @ApiModelProperty(value = "商品sku-id", required = true, example = "666")
    private String skuId;

    @NotNull(message = "商品数量不能为空！")
    @ApiModelProperty(value = "商品数量", required = true, example = "1")
    private Integer num;

}
