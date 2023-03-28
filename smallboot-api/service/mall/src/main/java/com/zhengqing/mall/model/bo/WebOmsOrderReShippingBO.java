package com.zhengqing.mall.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 商城-订单关联配送信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/29 5:59 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("web-商城-订单关联配送信息")
public class WebOmsOrderReShippingBO {

    @ApiModelProperty("物流公司编码")
    private String logisticsCode;

    @ApiModelProperty("物流单号")
    private String logisticsNo;

}
