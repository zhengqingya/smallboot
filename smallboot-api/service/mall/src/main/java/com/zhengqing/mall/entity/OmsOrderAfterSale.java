package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import com.zhengqing.mall.model.bo.OmsOrderReceiverAddressBO;
import com.zhengqing.mall.model.enums.OmsOrderAfterSaleSpuStatusEnum;
import com.zhengqing.mall.model.enums.OmsOrderAfterSaleStatusEnum;
import com.zhengqing.mall.model.enums.OmsOrderSaleTypeEnum;
import com.zhengqing.mall.model.enums.TpsLogisticsCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p>  商城-售后表 </p>
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
@TableName("oms_order_after_sale")
@ApiModel("商城-售后表")
public class OmsOrderAfterSale extends IsDeletedBaseEntity<OmsOrderAfterSale> {

    @ApiModelProperty("售后编号")
    @TableId(type = IdType.INPUT)
    private String afterSaleNo;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户电话")
    private String userPhone;

    /**
     * {@link OmsOrderSaleTypeEnum}
     */
    @ApiModelProperty("售后类型(1-退款 2-退货退款 3-换货)")
    private Byte afterType;

    /**
     * {@link OmsOrderAfterSaleStatusEnum}
     */
    @ApiModelProperty("售后状态(1->用户申请售后 2->用户撤销申请 3->同意申请 4->拒绝申请 5->申请退款 6->同意退款 7->拒绝退款 8->退款中 9->售后完成 10->已关闭)")
    private Byte afterStatus;

    /**
     * {@link OmsOrderAfterSaleSpuStatusEnum}
     */
//    @ApiModelProperty("货物状态(1-未发货 2-未收到货 3-收到货)")
//    private Byte spuStatus;

    @ApiModelProperty("退款,退/换货 原因")
    private String afterReason;

    @ApiModelProperty("说明")
    private String afterExplain;

    @ApiModelProperty("订单实付总金额(单位:分)")
    private Integer payPrice;

    @ApiModelProperty("订单运费(单位:分 0:包邮)")
    private Integer freight;

    @ApiModelProperty("手续费(单位：分)")
    private Integer procedurePrice;

    @ApiModelProperty("申请退款金额 (单位：分)")
    private Integer applyRefundPrice;

    @ApiModelProperty("实际退款金额 (单位：分)")
    private Integer refundPrice;

    @ApiModelProperty("退款时间")
    private Date refundTime;

    @ApiModelProperty("凭证图")
    private String certImgJson;

    @ApiModelProperty("售后申请时间")
    private Date applyTime;

    @ApiModelProperty("退/换货 物流公司")
    private String returnLogisticsCompany;

    /**
     * {@link TpsLogisticsCodeEnum }
     */
    @ApiModelProperty("物流公司编码")
    private String returnLogisticsCode;

    @ApiModelProperty("退/换货 物流单号")
    private String returnLogisticsNo;

    @ApiModelProperty("退/换货 收货地址")
    private String returnAddress;

    @ApiModelProperty("商店重发物流单号")
    private String againLogisticsNo;

    @ApiModelProperty("处理人ID")
    private Long handlerId;

    @ApiModelProperty("处理人姓名")
    private String handlerName;

    @ApiModelProperty("处理人结果-处理退款（true->同意 false->拒绝）")
    private Boolean handlerResultForRefund;

    @ApiModelProperty("处理人备注-处理退款")
    private String handlerRemarkForRefund;

    @ApiModelProperty("处理人处理时间-处理退款")
    private Date handlerTimeForRefund;

    @ApiModelProperty("处理人结果-处理申请（true->同意 false->拒绝）")
    private Boolean handlerResultForApply;

    @ApiModelProperty("处理人备注-处理申请")
    private String handlerRemarkForApply;

    @ApiModelProperty("处理人处理时间-处理申请")
    private Date handlerTimeForApply;

    @ApiModelProperty("售后关闭时间")
    private Date closeTime;

    @ApiModelProperty("售后关闭备注")
    private String closeRemark;

    @ApiModelProperty(value = "售后卖家自动关闭时间")
    private Date sellerAutoCloseTime;

    @ApiModelProperty(value = "售后买家自动关闭时间")
    private Date buyerAutoCloseTime;

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

}
