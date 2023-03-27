package com.zhengqing.mall.mini.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.core.custom.parameter.ParamCheck;
import com.zhengqing.mall.common.model.bo.OmsOrderReceiverAddressBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p> mini-商城-订单-修改地址-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/19 9:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("mini-商城-订单-修改地址-提交参数")
public class MiniOmsOrderUpdateReceiverAddressDTO implements ParamCheck {

    @NotNull(message = "订单号不能为空！")
    @ApiModelProperty(value = "订单号", required = true, example = "1624623037698")
    private String orderNo;

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

    @Valid
    @NotNull(message = "收货人地址信息不能为空")
    @ApiModelProperty(value = "收货人地址信息", required = true)
    private OmsOrderReceiverAddressBO receiverAddressObj;

    @Override
    public void checkParam() {
        this.receiverName = this.receiverAddressObj.getReceiverName();
        this.receiverPhone = this.receiverAddressObj.getReceiverPhone();
        this.receiverAddress = this.receiverAddressObj.getReceiverAddress();
    }

}
