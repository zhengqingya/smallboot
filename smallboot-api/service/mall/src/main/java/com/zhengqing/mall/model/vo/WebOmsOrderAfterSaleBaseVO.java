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

/**
 * <p>商城-售后列表base-展示视图</p>
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
@ApiModel("web-商城-售后列表base-展示视图")
public class WebOmsOrderAfterSaleBaseVO extends OmsOrderAfterSaleBaseVO {

    @ApiModelProperty("售后申请时间")
    private Date applyTime;


    // ======================= ↑↑↑↑↑↑ 售后主体详情 ↓↓↓↓↓↓ =======================

    // ======================= ↓↓↓↓↓↓ 其它详情 ↑↑↑↑↑↑ =======================

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
