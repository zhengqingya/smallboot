package com.zhengqing.mall.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>商城-商品-优惠券明细</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商城-商品-优惠券明细")
public class PmsSpuReCouponBO {

    @NotNull
    @ApiModelProperty(value = "优惠券ID", example = "1")
    private Long couponId;

    @NotBlank
    @ApiModelProperty(value = "优惠券名称", example = "买一送一")
    private String couponName;

    @NotNull
    @ApiModelProperty(value = "优惠券数量", example = "100")
    private Integer couponNum;


    @NotNull
    @ApiModelProperty(value = "商品的可用库存", example = "1")
    private Integer usableStock;

}
