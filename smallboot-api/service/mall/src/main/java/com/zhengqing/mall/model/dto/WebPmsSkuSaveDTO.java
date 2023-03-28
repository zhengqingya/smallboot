package com.zhengqing.mall.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.mall.model.bo.PmsSkuSpecBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.util.List;

/**
 * <p> 商城-商品规格保存明细 </p>
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
@ApiModel
public class WebPmsSkuSaveDTO extends BaseDTO {

    @JsonIgnore
    @ApiModelProperty(value = "商品ID", example = "1", hidden = true)
    private String spuId;

    @ApiModelProperty(value = "商品规格ID", example = "1")
    private String skuId;

    @NotEmpty(message = "商品规格不能为空！")
    @ApiModelProperty(value = "商品规格", required = true)
    private List<PmsSkuSpecBO> specList;

    @ApiModelProperty(value = "预售价格(单位:分)", example = "10")
    private Integer presellPrice;

    @NotNull(message = "销售价不能为空！")
    @ApiModelProperty(value = "销售价(单位:分)", required = true, example = "10")
    private Integer sellPrice;

    @ApiModelProperty(value = "每人限购", example = "10")
    private Integer limitCount;

    @Min(value = 0, message = "库存不能小于0")
    @Max(value = 10000, message = "库存不能大于10000")
    @NotNull(message = "总库存不能为空！")
    @ApiModelProperty(value = "总库存", required = true, example = "100")
    private Integer totalStock;

    @NotBlank(message = "sku封面不能为空！")
    @ApiModelProperty(value = "封面图", required = true, example = "https://www.zhengqingya.com/test.png")
    private String coverImg;

    @NotNull(message = "排序不能为空！")
    @ApiModelProperty(value = "排序", required = true, example = "1")
    private Integer sort;

    @NotBlank(message = "规格编码不能为空！")
    @ApiModelProperty(value = "规格编码", required = true, example = "ABC")
    private String code;

}
