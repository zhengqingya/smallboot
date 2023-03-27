package com.zhengqing.mall.web.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>商城-商品规格-响应参数</p>
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
@ApiModel("web-商城-商品规格-响应参数")
public class WebPmsSkuListVO extends BaseVO {

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("商品ID")
    private String spuId;

    @ApiModelProperty("规格编码")
    private String code;

    @ApiModelProperty("商品规格")
    private String skuJson;

    @ApiModelProperty("预售价格(单位:分)")
    private Integer presellPrice;

    @ApiModelProperty("销售价(单位:分)")
    private Integer salePrice;

    @ApiModelProperty("每人限购")
    private String limitCount;

    @ApiModelProperty("总库存")
    private Integer totalStock;

    @ApiModelProperty("可用库存")
    private Integer usableStock;

    @ApiModelProperty("已用库存")
    private Integer useStock;

    @ApiModelProperty("封面图")
    private String cover;

}
