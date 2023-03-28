package com.zhengqing.mall.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * <p> 商城-订单-确认收货-提交参数 </p>
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
@ApiModel("mini-商城-订单-确认收货-提交参数")
public class MiniOmsOrderConfirmReceiptDTO extends BaseDTO {

    @NotBlank(message = "订单号不能为空！")
    @ApiModelProperty(value = "订单号", required = true, example = "211018515967000578")
    private String orderNo;

//    @NotEmpty(message = "订单-关联商品ids不能为空！")
//    @ApiModelProperty(value = "订单-关联商品ids", required = true, example = "[1]")
//    private List<Long> orderItemIdList;

    //    @NotBlank(message = "配送ID不能为空！")
    @ApiModelProperty("配送ID(纯虚拟商品无此值)")
    private String shippingId;

    @JsonIgnore
    @ApiModelProperty(value = "租户id(后端mq自动收货使用)", hidden = true)
    private Integer tenantId;

}
