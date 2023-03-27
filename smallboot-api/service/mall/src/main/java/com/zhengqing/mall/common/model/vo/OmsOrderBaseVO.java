package com.zhengqing.mall.common.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.mall.common.model.enums.OmsOrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p>商城-订单base-展示视图</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-订单base-展示视图")
public class OmsOrderBaseVO extends BaseVO {

    @ApiModelProperty("订单编号")
    private String orderNo;

    /**
     * {@link OmsOrderStatusEnum}
     */
    @ApiModelProperty("订单状态")
    private Byte orderStatus;

    @ApiModelProperty("订单状态名称")
    private String orderStatusName;

    @ApiModelProperty(value = "未支付结束时间-待付款状态使用")
    private Date unPayEndTime;

    @ApiModelProperty("原价总金额(单位:分)")
    private Integer totalPrice;

    @ApiModelProperty("实付总金额(单位:分)")
    private Integer payPrice;

    public void handleData() {
        this.orderStatusName = OmsOrderStatusEnum.getEnum(this.orderStatus).getDesc();
    }


}
