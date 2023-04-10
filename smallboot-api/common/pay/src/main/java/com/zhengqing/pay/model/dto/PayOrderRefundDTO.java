package com.zhengqing.pay.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p> 支付-订单退款-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/20 17:38
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("支付-订单退款-请求参数")
public class PayOrderRefundDTO extends BaseDTO {

    @NotNull
    @ApiModelProperty("租户id")
    private Integer tenantId;

    @ApiModelProperty("内部系统订单号")
    private String orderNo;

    @ApiModelProperty("内部系统退款订单号")
    private String refundOrderNo;

    @ApiModelProperty("总金额")
    private Integer totalPrice;

    @ApiModelProperty("退款金额")
    private Integer refundPrice;

    @ApiModelProperty("退款描述")
    private String refundDesc;

}
