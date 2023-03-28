package com.zhengqing.mall.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 商城-订单-配送商品-展示视图 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/18 14:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商城-订单-配送商品-展示视图")
public class OmsOrderShippingItemVO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("封面图")
    private String coverImg;

}
