package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zhengqing.mall.common.model.bo.PmsSkuSpecBO;
import com.zhengqing.mall.common.model.enums.OmsOrderItemStatusEnum;
import com.zhengqing.mall.common.model.enums.PmsSpuTypeEnum;
import com.zhengqing.mall.config.mybatis.handler.MallListSpecTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * <p>  商城-订单详情 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "oms_order_item", autoResultMap = true)
@ApiModel("商城-订单详情")
public class OmsOrderItem extends Model<OmsOrderItem> {

    @ApiModelProperty("ID")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("商品ID")
    private String spuId;

    @ApiModelProperty("商品sku-id")
    private String skuId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("封面图")
    private String coverImg;

    @ApiModelProperty("商品规格属性")
    @TableField(typeHandler = MallListSpecTypeHandler.class)
    private List<PmsSkuSpecBO> specList;

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
     * {@link OmsOrderItemStatusEnum}
     */
    @ApiModelProperty("状态(1->待支付 2->已取消 3->未发货 4->已发货 5->已完成)")
    private Byte status;

    @ApiModelProperty("是否已经评价(false->否 true->是)")
    private Boolean isRate;


    // ============================ ↓↓↓↓↓↓ 优惠券信息 ↓↓↓↓↓↓ ============================

    @ApiModelProperty("优惠券ID")
    private Long couponId;

    @ApiModelProperty("优惠券名称")
    private String couponName;

    @ApiModelProperty("优惠券数量")
    private Integer couponNum;

    @ApiModelProperty("是否发放优惠券(1->是，0->否)")
    private Boolean isSendCoupon;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
