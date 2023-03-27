package com.zhengqing.mall.web.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p> 商城-订单详情-响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/30 17:30
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-订单详情-响应参数")
public class WebOmsOrderDetailVO extends WebOmsOrderBaseVO {

    @ApiModelProperty("支付流水号")
    private String payNo;

    @ApiModelProperty("支付时间")
    private Date payTime;

    @ApiModelProperty("订单备注")
    private String orderRemark;

    @Override
    public void handleData() {
        super.handleData();
    }


}
