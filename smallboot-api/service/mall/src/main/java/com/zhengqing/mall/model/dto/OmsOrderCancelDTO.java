package com.zhengqing.mall.model.dto;

import com.zhengqing.common.core.custom.parameter.CheckParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * <p> 商城-订单取消-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 10:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商城-订单取消-提交参数")
public class OmsOrderCancelDTO implements CheckParam {

    @NotBlank(message = "订单编号不能为空！")
    @ApiModelProperty(value = "订单编号", required = true, example = "1624623037698")
    private String orderNo;

//    @NotEmpty(message = "取消原因不能为空！")
//    @ApiModelProperty(value = "取消原因", example = "其它")
//    private String reason;

    @Override
    public void checkParam() {
//        if (StringUtils.isBlank(this.reason)) {
//            this.reason = "其它";
//        }
    }

}
