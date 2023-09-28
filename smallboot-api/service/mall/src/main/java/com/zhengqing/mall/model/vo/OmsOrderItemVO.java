package com.zhengqing.mall.model.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.mall.model.bo.PmsSkuSpecBO;
import com.zhengqing.mall.enums.OmsOrderItemStatusEnum;
import com.zhengqing.mall.enums.OmsOrderItemTypeEnum;
import com.zhengqing.mall.enums.PmsSpuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p> 商城-订单-商品-展示视图 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/18 14:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商城-订单-商品-展示视图")
public class OmsOrderItemVO {

    @ApiModelProperty("ID")
    private String id;

    @JsonIgnore
    @ApiModelProperty(value = "订单编号", hidden = true)
    private String orderNo;

    @ApiModelProperty("是否售后处理 -- 售后中/售后完成 (true->是 false->否)")
    private Boolean isAfterSale;

    /**
     * {@link OmsOrderItemStatusEnum}
     */
    @ApiModelProperty(value = "状态")
    private Byte status;

    @ApiModelProperty("状态名称")
    private String statusName;

    @ApiModelProperty("商品ID")
    private String spuId;

    @ApiModelProperty("商品sku-id")
    private String skuId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("封面图")
    private String coverImg;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("单价(单位:分)")
    private Integer price;

    @ApiModelProperty("总价(单位:分)")
    private Integer totalPrice;

    /**
     * {@link PmsSpuTypeEnum}
     */
    @ApiModelProperty("类型")
    private Byte type;

    /**
     * {@link OmsOrderItemTypeEnum}
     */
    @ApiModelProperty("类型值(返回前端的时候，只有实物或虚拟2种值)")
    private String typeName;

    @ApiModelProperty("是否已经评价(false->否 true->是)")
    private Boolean isRate;

    @ApiModelProperty(value = "商品规格属性")
    private List<PmsSkuSpecBO> specList;

    public void handleData() {
        this.statusName = OmsOrderItemStatusEnum.getEnum(this.status).getDesc();
        this.typeName = OmsOrderItemTypeEnum.getEnumBySpuType(this.type).getDesc();
        if (this.isAfterSale == null) {
            this.isAfterSale = false;
        }
    }

}
