package com.zhengqing.mall.mini.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 商城-订单-商品状态-参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/30 10:24 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("mini-商城-订单-商品状态-参数")
public class MiniOmsOrderItemStatusBO {

    @ApiModelProperty("ID")
    private String orderItemId;

    @ApiModelProperty("订单状态")
    private Byte orderStatus;

}
