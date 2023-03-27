package com.zhengqing.mall.common.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * <p> 商城-订单收货信息-新增参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 10:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商城-订单收货信息-参数")
public class OmsOrderReceiverAddressBO {

//    @NotNull(message = "地址id不能为空！")
//    @ApiModelProperty(value = "地址id", required = true, example = "1")
//    private Integer addressId;

    @NotEmpty(message = "收件人姓名不能为空！")
    @ApiModelProperty(value = "收货人姓名", required = true, example = "皮卡丘")
    private String receiverName;

    @NotEmpty(message = "收件人电话不能为空！")
    @ApiModelProperty(value = "收货人电话", required = true, example = "88888888")
    private String receiverPhone;

//    @NotEmpty(message = "收货地址不能为空！")
//    @ApiModelProperty(value = "收货人省", required = true, example = "四川省")
//    private String receiverPrince;
//
//    @NotEmpty(message = "收货地址不能为空！")
//    @ApiModelProperty(value = "收货人市", required = true, example = "成都市")
//    private String receiverCity;
//
//    @NotEmpty(message = "收货地址不能为空！")
//    @ApiModelProperty(value = "收货人区", required = true, example = "武侯区")
//    private String receiverArea;

    @NotEmpty(message = "收货地址不能为空！")
    @ApiModelProperty(value = "收货人地址", required = true, example = "四川省成都市高新区天府三街")
    private String receiverAddress;

//    @NotEmpty(message = "收货地址不能为空！")
//    @ApiModelProperty(value = "收货人地址详情", required = true, example = "42楼")
//    private String receiverAddressDetail;

}
