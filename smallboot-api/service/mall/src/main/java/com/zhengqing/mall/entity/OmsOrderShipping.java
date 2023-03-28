package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zhengqing.mall.model.enums.OmsOrderShippingReceiptTypeEnum;
import com.zhengqing.mall.model.enums.TpsLogisticsCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p>  商城-订单配送表 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oms_order_shipping")
@ApiModel("商城-订单配送表")
public class OmsOrderShipping extends Model<OmsOrderShipping> {

    @ApiModelProperty("ID")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    // ========================== ↓↓↓↓↓↓ 收货人信息 ↓↓↓↓↓↓ ==========================

//    @ApiModelProperty("地址id")
//    private Integer addressId;

    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @ApiModelProperty("收货人电话")
    private String receiverPhone;

//    @ApiModelProperty("收货人省")
//    private String receiverPrince;
//
//    @ApiModelProperty("收货人市")
//    private String receiverCity;
//
//    @ApiModelProperty("收货人区")
//    private String receiverArea;

    @ApiModelProperty("收货人地址")
    private String receiverAddress;

//    @ApiModelProperty("收货人地址详情")
//    private String receiverAddressDetail;
//
//    @ApiModelProperty("收货人邮政编码")
//    private String receiverZipcode;

    // ========================== ↓↓↓↓↓↓ 发货人信息 ↓↓↓↓↓↓ ==========================

    @ApiModelProperty("发货人")
    private String delivererName;

    @ApiModelProperty("发货人电话")
    private String delivererPhone;

    @ApiModelProperty("发货地址")
    private String delivererAddress;

    // ========================== ↓↓↓↓↓↓ 其它 ↓↓↓↓↓↓ ==========================

    @ApiModelProperty("发货时间")
    private Date deliverTime;

    @ApiModelProperty("物流公司")
    private String logisticsCompany;

    /**
     * {@link TpsLogisticsCodeEnum }
     */
    @ApiModelProperty("物流公司编码")
    private String logisticsCode;

    @ApiModelProperty("物流单号")
    private String logisticsNo;

    @ApiModelProperty("微信通知消息")
    private String wxNoticeMsg;

    /**
     * {@link OmsOrderShippingReceiptTypeEnum }
     */
//    @ApiModelProperty("收货类型(1->手动收货 2->自动收货)")
//    private Byte receiptType;

    @ApiModelProperty("收货时间")
    private Date receiptTime;

    @ApiModelProperty(value = "自动收货时间")
    private Date autoReceiptTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
