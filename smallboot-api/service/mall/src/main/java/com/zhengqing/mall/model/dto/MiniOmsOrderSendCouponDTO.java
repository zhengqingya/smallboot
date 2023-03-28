package com.zhengqing.mall.model.dto;

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
 * <p> 商城-订单-一键领取优惠券-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/20 10:43 下午
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商城-订单-一键领取优惠券-提交参数")
public class MiniOmsOrderSendCouponDTO extends BaseDTO {

    @NotNull(message = "订单号不能为空！")
    @ApiModelProperty(value = "订单号", required = true, example = "211018515967000578")
    private String orderNo;

//    @NotEmpty(message = "优惠券ids不能为空")
//    @ApiModelProperty(value = "优惠券ids", required = true, example = "1")
//    private List<Long> couponIdList;

    @NotNull(message = "操作用户id不能为空")
    @ApiModelProperty(value = "操作用户id", required = true, example = "1435486752507736065")
    private Long userId;

}
