package com.zhengqing.mall.model.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.mall.model.bo.OmsOrderReceiverAddressBO;
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
 * <p> 商城-订单-详情-展示视图 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 11:05
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商城-订单-详情-展示视图")
public class MiniOmsOrderDetailVO extends MiniOmsOrderBaseVO {

    @ApiModelProperty("支付流水号")
    private String payNo;

    @ApiModelProperty("支付时间")
    private Date payTime;

    @ApiModelProperty("运费(单位:分 0:包邮)")
    private Integer freight;

    @ApiModelProperty("订单备注")
    private String orderRemark;

    // ========================== ↓↓↓↓↓↓ 收货人信息 ↓↓↓↓↓↓ ==========================

    /**
     * {@link OmsOrderReceiverAddressBO }
     */
    @JsonIgnore
    @ApiModelProperty(value = "收货人姓名", hidden = true)
    private String receiverName;

    @JsonIgnore
    @ApiModelProperty(value = "收货人电话", hidden = true)
    private String receiverPhone;

    @JsonIgnore
    @ApiModelProperty(value = "收货人地址", hidden = true)
    private String receiverAddress;

    @ApiModelProperty("收货人地址信息（注：目前暂时只支持单个发货）")
    private OmsOrderReceiverAddressBO receiverAddressObj;

    // ========================== ↑↑↑↑↑↑ 收货人信息 ↑↑↑↑↑↑ ==========================

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "自动收货时间")
    private Date autoReceiptTime;

    // ======================= ↑↑↑↑↑↑ 订单主体详情 ↓↓↓↓↓↓ =======================

    // ======================= ↓↓↓↓↓↓ 订单其它详情 ↑↑↑↑↑↑ =======================

    @ApiModelProperty(value = "订单关联商品信息")
    private List<OmsOrderItemVO> spuList;

    @ApiModelProperty(value = "订单关联配送信息")
    private List<OmsOrderShippingVO> shippingList;

    @ApiModelProperty("是否发放优惠券(true->是，false->否)")
    private Boolean isSendCoupon;

    @Override
    public void handleData() {
        super.handleData();
        this.receiverAddressObj = OmsOrderReceiverAddressBO.builder()
                .receiverName(this.receiverName)
                .receiverPhone(this.receiverPhone)
                .receiverAddress(this.receiverAddress)
                .build();
    }

}
