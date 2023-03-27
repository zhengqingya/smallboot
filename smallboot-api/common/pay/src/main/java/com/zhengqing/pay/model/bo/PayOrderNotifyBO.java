package com.zhengqing.pay.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 支付成功通知参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/4 11:53
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("支付成功通知参数")
public class PayOrderNotifyBO extends BaseBO {

    /**
     * 租户ID
     */
    private Integer tenantId;

    /**
     * 支付订单号
     */
    private String transactionId;

    /**
     * 内部系统订单号
     */
    private String orderNo;

    /**
     * 系统退款订单号
     */
    private String outRefundNo;

    /**
     * 业务状态
     * 退款: true-(SUCCESS-退款成功) false -(CHANGE-退款异常，REFUNDCLOSE—退款关闭)
     * 支付:true-SUCCESS false-FAIL
     */
    private Boolean resultStatus;

}
