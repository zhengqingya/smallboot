package com.zhengqing.mall.model.bo;


import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>商城-分类-规格明细</p>
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
@ApiModel("mini-商城-分类-规格明细")
public class MiniPmsCategoryReSkuBO extends BaseBO {

    @ApiModelProperty(value = "商品规格ID", example = "1")
    private String id;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "销售价(单位:分)", example = "10")
    private Integer sellPrice;

    @ApiModelProperty(value = "可用库存", example = "100")
    private Integer usableStock;

}
