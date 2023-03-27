package com.zhengqing.mall.web.model.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> web-商城-订单信息-批量发货信息 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("web-商城-订单信息-批量发货信息")
public class WebOmsOrderImportSendSpuBO {

    @ExcelProperty("序号")
    @NotNull(message = "序号不能为空！")
    @ApiModelProperty(value = "序号", required = true, example = "1624623037698")
    private String id;

    @ExcelProperty("订单编号")
    @NotNull(message = "订单号不能为空！")
    @ApiModelProperty(value = "订单号", required = true, example = "1624623037698")
    private String orderNo;

    /**
     * {@link com.zhengqing.mall.common.model.enums.TpsLogisticsCodeEnum }
     */
    @ExcelProperty("配送方式")
    @NotBlank(message = "物流公司不能为空！")
    @ApiModelProperty(value = "快递公司编码", required = true, example = "ZTO")
    private String logisticsCode;

    @ExcelProperty("物流编号")
    @NotBlank(message = "物流单号不能为空！")
    @ApiModelProperty(value = "物流单号", required = true, example = "000000000000")
    private String logisticsNo;

    @ExcelProperty("微信消息通知")
    @NotBlank(message = "微信通知消息不能为空！")
    @ApiModelProperty(value = "微信通知消息", required = true, example = "商品已发出，请注意查收！")
    private String wxNoticeMsg;


}
