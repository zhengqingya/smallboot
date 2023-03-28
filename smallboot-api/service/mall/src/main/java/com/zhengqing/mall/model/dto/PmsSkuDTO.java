package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p> 商品-购物车列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/8 13:58
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商品-购物车列表-请求参数")
public class PmsSkuDTO extends BaseDTO {

    @NotEmpty(message = "商品sku-id不能为空！")
    @ApiModelProperty("商品sku-id")
    private List<String> skuIdList;

}
