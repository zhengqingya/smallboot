package com.zhengqing.mall.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <p> mini-商城-待支付订单-支付-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/19 9:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("mini-商城-待支付订单-支付-提交参数")
public class MiniOmsOrderPayDTO {

    @NotNull(message = "订单编号不能为空！")
    @ApiModelProperty(value = "订单编号", required = true, example = "1624623037698")
    private String orderNo;

}
