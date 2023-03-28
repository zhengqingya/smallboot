package com.zhengqing.mall.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> mini-商城-订单详情-用户历史购买数-参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/14 15:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("mini-商城-订单详情-用户历史购买数-参数")
public class MiniOmsOrderItemBuyLimitBO {

    @ApiModelProperty("商品sku-id")
    private String skuId;

    @ApiModelProperty("数量")
    private Integer num;

}
