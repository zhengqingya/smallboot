package com.zhengqing.mall.mini.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
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
 * <p>商城-购物车-商品规格明细</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商城-购物车-商品规格明细")
public class MiniOmsCartSkuBO extends BaseBO {

    @ApiModelProperty(value = "商品规格ID", example = "1")
    private String id;

    @ApiModelProperty(value = "预售价格(单位:分)", example = "10")
    private Integer presellPrice;

    @NotNull(message = "销售价不能为空！")
    @ApiModelProperty(value = "销售价(单位:分)", required = true, example = "10")
    private Integer sellPrice;

    @ApiModelProperty(value = "每人限购", example = "10")
    private Integer limitCount;

    @ApiModelProperty(value = "总库存", example = "100")
    private Integer totalStock;

    @ApiModelProperty(value = "已用库存", example = "0")
    private Integer useStock;

    @Min(value = 0, message = "库存不能小于0")
    @NotNull(message = "可用库存不能为空！")
    @ApiModelProperty(value = "可用库存", required = true, example = "100")
    private Integer usableStock;

    @NotBlank(message = "商品封面不能为空！")
    @ApiModelProperty(value = "封面图", required = true, example = "https://www.zhengqingya.com/test.png")
    private String coverImg;

    public void handleData() {
    }

}
