package com.zhengqing.mall.model.dto;

import com.zhengqing.mall.enums.TpsLogisticsCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p> 积分商城-订单发货-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 10:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("web-商城-订单发货-提交参数")
public class WebOmsOrderSendSpuDTO {

    @NotNull(message = "订单号不能为空！")
    @ApiModelProperty(value = "订单号", required = true, example = "1624623037698")
    private String orderNo;

    @NotEmpty(message = "订单关联商品ids不能为空")
    @ApiModelProperty(value = "订单关联商品ids", required = true, example = "[1]")
    private List<String> orderItemIdList;

    /**
     * {@link TpsLogisticsCodeEnum }
     */
    @NotBlank(message = "物流公司不能为空！")
    @ApiModelProperty(value = "快递公司编码", required = true, example = "ZTO")
    private String logisticsCode;

    /**
     * {@link TpsLogisticsCodeEnum }
     */
//    @NotBlank(message = "物流公司不能为空！")
//    @ApiModelProperty(value = "物流公司", required = true, example = "顺丰快递")
//    private String logisticsCompany;

    @NotBlank(message = "物流单号不能为空！")
    @ApiModelProperty(value = "物流单号", required = true, example = "000000000000")
    private String logisticsNo;

    @NotBlank(message = "微信通知消息不能为空！")
    @ApiModelProperty(value = "微信通知消息", required = true, example = "商品已发出，请注意查收！")
    private String wxNoticeMsg;

    // ========================== ↓↓↓↓↓↓ 收货人信息 ↓↓↓↓↓↓ ==========================

    @NotBlank(message = "收货人姓名不能为空！")
    @ApiModelProperty(value = "收货人姓名", required = true, example = "我是收货人")
    private String receiverName;

    @NotBlank(message = "收货人电话不能为空！")
    @ApiModelProperty(value = "收货人电话", required = true, example = "15183303003")
    private String receiverPhone;

    @NotBlank(message = "收货人地址不能为空！")
    @ApiModelProperty(value = "收货人地址", required = true, example = "四川省成都市高新区天府三街")
    private String receiverAddress;


    // ========================== ↓↓↓↓↓↓ 发货人信息 ↓↓↓↓↓↓ ==========================

    //    @NotBlank(message = "发货人不能为空！")
    @ApiModelProperty(value = "发货人", required = true, example = "我是发货人")
    private String delivererName;

    //    @NotBlank(message = "发货人电话不能为空！")
    @ApiModelProperty(value = "发货人电话", required = true, example = "15183303001")
    private String delivererPhone;

    //    @NotBlank(message = "发货地址不能为空！")
    @ApiModelProperty(value = "发货地址", required = true, example = "四川省成都市高新区天府三街")
    private String delivererAddress;

}
