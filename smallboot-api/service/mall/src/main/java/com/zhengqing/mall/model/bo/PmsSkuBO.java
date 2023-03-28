package com.zhengqing.mall.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>商城-商品规格明细</p>
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
@ApiModel("商城-商品规格明细")
public class PmsSkuBO extends BaseBO {

    @ApiModelProperty(value = "商品规格ID", example = "1")
    private String id;

    @ApiModelProperty(value = "商品ID", example = "1")
    private String spuId;

    @ApiModelProperty(value = "规格编码", example = "ABC")
    private String code;

    @ApiModelProperty(value = "商品规格", required = true)
    private List<PmsSkuSpecBO> specList;

    @ApiModelProperty(value = "预售价格(单位:分)", example = "10")
    private Integer presellPrice;

    @ApiModelProperty(value = "销售价(单位:分)", example = "10")
    private Integer sellPrice;

    @ApiModelProperty(value = "每人限购", example = "10")
    private Integer limitCount;

    @ApiModelProperty(value = "总库存", example = "100")
    private Integer totalStock;

    @ApiModelProperty(value = "可用库存", example = "100")
    private Integer usableStock;

    @ApiModelProperty(value = "已用库存", example = "0")
    private Integer useStock;

    @ApiModelProperty(value = "封面图", example = "https://www.zhengqingya.com/test.png")
    private String coverImg;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "是否显示", example = "true")
    private Boolean isShow;

}
