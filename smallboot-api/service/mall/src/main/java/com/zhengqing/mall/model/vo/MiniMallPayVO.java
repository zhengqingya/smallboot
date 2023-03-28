package com.zhengqing.mall.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 商城-创建订单-展示视图 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/26 15:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("mini-商城-创建订单-展示视图")
public class MiniMallPayVO {

    @ApiModelProperty("订单号")
    private String orderNo;

}
