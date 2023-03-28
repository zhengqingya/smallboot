package com.zhengqing.mall.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.dto.BasePageDTO;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.core.custom.parameter.ParamCheck;
import com.zhengqing.mall.model.enums.OmsOrderStatusEnum;
import com.zhengqing.mall.model.enums.WebOmsOrderTabEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p> 商城-订单信息-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-订单信息-分页列表-请求参数")
public class WebOmsOrderPageDTO extends BasePageDTO implements ParamCheck {

    /**
     * {@link WebOmsOrderTabEnum}
     */
    @ApiModelProperty("tab条件")
    private Byte tabValue;

    /**
     * {@link OmsOrderStatusEnum}
     */
//    @Range(min = 1, max = 5, message = "订单状态值：1-5")
    @JsonIgnore
    @ApiModelProperty(value = "订单状态", hidden = true)
    private Byte orderStatus;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("物流公司")
    private String logisticsCompany;

    @ApiModelProperty("物流单号")
    private String logisticsNo;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户电话")
    private String userPhone;

    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @ApiModelProperty("收货人电话")
    private String receiverPhone;

    @ApiModelProperty("收货人电话后4位")
    private String receiverPhoneAfterFour;

    //    @Length(min = 19, max = 19, message = "时间格式不正确")
    @ApiModelProperty(value = "开始时间(下单时间)", example = "2021-06-06 10:10:55")
    @DateTimeFormat(pattern = MyDateUtil.DATE_TIME_FORMAT, iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startTime;

    //    @Length(min = 19, max = 19, message = "时间格式不正确")
    @ApiModelProperty(value = "结束时间(下单时间)", example = "2022-06-06 10:10:55")
    @DateTimeFormat(pattern = MyDateUtil.DATE_TIME_FORMAT, iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endTime;

    @Override
    public void checkParam() {
        if (this.tabValue != null) {
            WebOmsOrderTabEnum orderTabEnum = WebOmsOrderTabEnum.getEnum(this.tabValue);
            switch (orderTabEnum) {
                case ALL:
                    this.orderStatus = null;
                    break;
                case UN_PAY:
                    this.orderStatus = OmsOrderStatusEnum.UN_PAY.getStatus();
                    break;
                case UN_BILL:
                    this.orderStatus = OmsOrderStatusEnum.UN_BILL.getStatus();
                    break;
                case BILL:
                    this.orderStatus = OmsOrderStatusEnum.BILL.getStatus();
                    break;
                case FINISH:
                    this.orderStatus = OmsOrderStatusEnum.FINISH.getStatus();
                    break;
                case CANC:
                    this.orderStatus = OmsOrderStatusEnum.CANC.getStatus();
                    break;
                default:
                    break;
            }
        }
    }

}
