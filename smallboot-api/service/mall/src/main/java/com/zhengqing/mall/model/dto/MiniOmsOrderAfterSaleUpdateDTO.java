package com.zhengqing.mall.model.dto;

import com.zhengqing.mall.model.enums.OmsOrderSaleTypeEnum;
import com.zhengqing.mall.model.enums.TpsLogisticsCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> 商城-申请售后-更新-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 14:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("mini-商城-申请售后-更新-提交参数")
public class MiniOmsOrderAfterSaleUpdateDTO {

    @NotBlank(message = "售后编号不能为空！")
    @ApiModelProperty(value = "售后编号", required = true, example = "1")
    private String afterSaleNo;

//    @NotBlank(message = "退/换货 物流公司不能为空")
//    @ApiModelProperty(value = "物流公司", required = true, example = "顺丰快递")
//    private String returnLogisticsCompany;

    /**
     * {@link TpsLogisticsCodeEnum }
     */
    @NotBlank(message = "退/换货 物流公司编码不能为空")
    @ApiModelProperty(value = "退/换货 物流公司编码", required = true, example = "STO")
    private String returnLogisticsCode;

    @NotBlank(message = "物流公司不能为空")
    @ApiModelProperty(value = "退/换货 物流单号", required = true, example = "88888888")
    private String returnLogisticsNo;

    //    @NotBlank(message = "退/换货 收货地址不能为空")
    @ApiModelProperty(value = "退/换货 收货地址", example = "四川省成都市高新区天府三街")
    private String returnAddress;

    /**
     * {@link OmsOrderSaleTypeEnum}
     */
    @NotNull(message = "售后类型不能为空！")
    @Range(min = 2, max = 2, message = "暂只能处理退货退款数据！")
    @ApiModelProperty("售后类型(1-退款 2-退货退款 3-换货)")
    private Byte afterType;

}
