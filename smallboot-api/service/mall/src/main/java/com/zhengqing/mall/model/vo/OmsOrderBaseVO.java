package com.zhengqing.mall.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.mall.model.bo.OmsOrderReceiverAddressBO;
import com.zhengqing.mall.model.enums.OmsOrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

/**
 * <p>商城-订单base-展示视图</p>
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
@ApiModel("商城-订单base-展示视图")
public class OmsOrderBaseVO extends BaseVO {

    // ========================== ↓↓↓↓↓↓ 订单主体详情 ↓↓↓↓↓↓ ==========================

    @ApiModelProperty("订单编号")
    private String orderNo;

    /**
     * {@link OmsOrderStatusEnum}
     */
    @ApiModelProperty("订单状态")
    private Byte orderStatus;

    @ApiModelProperty("订单状态名称")
    private String orderStatusName;

    @ApiModelProperty(value = "未支付结束时间-待付款状态使用")
    private Date unPayEndTime;

    @ApiModelProperty("原价总金额(单位:分)")
    private Integer totalPrice;

    @ApiModelProperty("实付总金额(单位:分)")
    private Integer payPrice;

    @ApiModelProperty("支付流水号")
    private String payNo;

    @ApiModelProperty("支付时间")
    private Date payTime;

    @ApiModelProperty("订单备注")
    private String orderRemark;

    @ApiModelProperty("运费(单位:分 0:包邮)")
    private Integer freight;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户电话")
    private String userPhone;

    @ApiModelProperty(value = "自动收货时间")
    private Date autoReceiptTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

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

    // ========================== ↓↓↓↓↓↓ 其它信息 ↓↓↓↓↓↓ ==========================

    @ApiModelProperty(value = "订单关联配送信息")
    private List<OmsOrderDeliverVO> deliverList;

    @ApiModelProperty("订单关联商品信息")
    private List<OmsOrderItemVO> spuList;

    @ApiModelProperty("是否售后处理 -- 售后中/售后完成 (true->是 false->否)")
    private Boolean isAfterSale;

    @ApiModelProperty(value = "售后处理截止时间")
    private Date afterSaleEndTime;

    @ApiModelProperty("商品名称(多个以英文逗号分隔)")
    private String spuNames;

    @ApiModelProperty("商品数量(关联所有商品的总件数)")
    private Integer spuNum;

    @ApiModelProperty("封面图")
    private String coverImg;

    @ApiModelProperty("是否发放优惠券(true->是，false->否)")
    private Boolean isSendCoupon;


    public void handleData() {
        this.orderStatusName = OmsOrderStatusEnum.getEnum(this.orderStatus).getDesc();

        this.receiverAddressObj = OmsOrderReceiverAddressBO.builder()
                .receiverName(this.receiverName)
                .receiverPhone(this.receiverPhone)
                .receiverAddress(this.receiverAddress)
                .build();
        if (this.isAfterSale == null) {
            this.isAfterSale = false;
        }
        if (!CollectionUtils.isEmpty(this.spuList)) {
            this.spuList.forEach(item -> {
                if (item.getIsAfterSale() == null) {
                    item.setIsAfterSale(false);
                }
            });
        }

        this.coverImg = this.getSpuList().get(0).getCoverImg();
        StringJoiner spuNameSj = new StringJoiner(",");
        this.spuNum = 0;
        this.getSpuList().forEach(item -> {
            spuNameSj.add(item.getName());
            this.spuNum += item.getNum();
        });
        this.spuNames = spuNameSj.toString();
    }

}
