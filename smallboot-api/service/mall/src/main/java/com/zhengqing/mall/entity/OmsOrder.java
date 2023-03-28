package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import com.zhengqing.mall.model.bo.OmsOrderReceiverAddressBO;
import com.zhengqing.mall.model.enums.OmsOrderDeliverTypeEnum;
import com.zhengqing.mall.model.enums.OmsOrderStatusEnum;
import com.zhengqing.mall.model.enums.OmsOrderStockCheckTypeEnum;
import com.zhengqing.mall.model.enums.OmsOrderSourceEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p>  商城-订单信息 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oms_order")
@ApiModel("商城-订单信息")
public class OmsOrder extends IsDeletedBaseEntity<OmsOrder> {

    @TableId(type = IdType.INPUT)
    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("微信openid")
    private String wxOpenid;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户电话")
    private String userPhone;

    /**
     * {@link UserSexEnum}
     */
    @ApiModelProperty("性别")
    private Byte userSex;

    @ApiModelProperty("支付流水号")
    private String payNo;

    @ApiModelProperty("支付时间")
    private Date payTime;

    //    @TableField(value = "un_pay_end_time", updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "未支付结束时间")
    private Date unPayEndTime;

    @ApiModelProperty("原价总金额(单位:分)")
    private Integer totalPrice;

    @ApiModelProperty("实付总金额(单位:分)")
    private Integer payPrice;

    @ApiModelProperty("运费(单位:分 0:包邮)")
    private Integer freight;

    /**
     * {@link OmsOrderStatusEnum}
     */
    @ApiModelProperty("订单状态")
    private Byte orderStatus;

    /**
     * {@link OmsOrderSourceEnum}
     */
    @ApiModelProperty("订单来源")
    private Byte orderSource;

    @ApiModelProperty("订单备注")
    private String orderRemark;

//    @ApiModelProperty("买家昵称")
//    private String buyerNick;

    @ApiModelProperty("买家留言")
    private String buyerMsg;

    @ApiModelProperty("订单完成时间")
    private Date orderEndTime;

    @ApiModelProperty("订单关闭时间")
    private Date orderCloseTime;

    /**
     * {@link OmsOrderDeliverTypeEnum}
     */
    @ApiModelProperty("发货方式(1->快递 2->无需发货)")
    private Byte deliverType;

    // ========================== ↓↓↓↓↓↓ 收货人信息 ↓↓↓↓↓↓ ==========================

    /**
     * {@link OmsOrderReceiverAddressBO }
     */
    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @ApiModelProperty("收货人电话")
    private String receiverPhone;

    @ApiModelProperty("收货人地址")
    private String receiverAddress;

    // ========================== ↑↑↑↑↑↑ 收货人信息 ↑↑↑↑↑↑ ==========================

    /**
     * {@link OmsOrderStockCheckTypeEnum}
     */
    @ApiModelProperty("减库存设置（1：提交订单减库存 2：付款减库存）")
    private Byte stockCheckType;

    @ApiModelProperty(value = "售后处理截止时间")
    private Date afterSaleEndTime;

    @ApiModelProperty(value = "自动收货时间")
    private Date autoReceiptTime;

    @ApiModelProperty("是否发放优惠券(true->是，false->否)")
    private Boolean isSendCoupon;

}
