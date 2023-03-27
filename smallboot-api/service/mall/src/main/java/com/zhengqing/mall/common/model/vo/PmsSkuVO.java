package com.zhengqing.mall.common.model.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.mall.common.model.bo.PmsSkuSpecBO;
import com.zhengqing.mall.common.model.enums.OmsOrderDeliverTypeEnum;
import com.zhengqing.mall.common.model.enums.PmsSpuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * <p>商城-商品sku-展示视图</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class PmsSkuVO extends BaseVO {

    @ApiModelProperty("商品sku-id")
    private String skuId;

    @ApiModelProperty("商品id")
    private String spuId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("封面图")
    private String coverImg;

    /**
     * {@link PmsSpuTypeEnum}
     */
    @ApiModelProperty("类型")
    private Byte type;

    @ApiModelProperty("运费(单位:分 0:包邮)")
    private Integer freight;

    @ApiModelProperty("是否预售(false->否；true->是)")
    private Boolean isPresell;

    @ApiModelProperty("预售开始时间")
    private Date presellStartTime;

    @ApiModelProperty("预售结束时间")
    private Date presellEndTime;

    @ApiModelProperty("预售价格(单位:分)")
    private Integer presellPrice;

    @JsonIgnore
    @ApiModelProperty(value = "商品规格-属性", hidden = true)
    private List<PmsSkuSpecBO> specList;

    @ApiModelProperty("商品销售价格(单位:分)")
    private Integer sellPrice;

    @ApiModelProperty("可用库存")
    private Integer usableStock;

    @ApiModelProperty("每人限购")
    private Integer limitCount;

    @ApiModelProperty("是否限购")
    private Boolean isLimit;

    @ApiModelProperty("商品价格(单位:分) - 预售时：预售价格不为空则取预售价格，若为空取销售价格  正常销售：取销售价格")
    private Integer price;

    /**
     * {@link OmsOrderDeliverTypeEnum}
     */
    @JsonIgnore
    @ApiModelProperty(value = "发货方式", hidden = true)
    private OmsOrderDeliverTypeEnum deliverTypeEnum;

    public void handleData() {
        if (this.isPresell) {
            // 预售结束时间之前取销售价格，之后取预售价格
            if (this.presellPrice == null || this.presellPrice == 0) {
                // 如果预售价格为空，直接取销售价格
                this.price = this.sellPrice;
            } else {
                this.price = new Date().after(this.presellEndTime) ? this.sellPrice : this.presellPrice;
            }
            // 是否预售值根据预售时间动态计算
            this.isPresell = new Date().before(this.presellEndTime);
        } else {
            this.price = this.sellPrice;
        }
        this.isLimit = this.limitCount != 0;
        this.deliverTypeEnum = PmsSpuTypeEnum.getDeliverTypeEnum(this.type);
    }

}
