package com.zhengqing.mall.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.context.UmsUserContext;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.model.dto.BasePageDTO;
import com.zhengqing.common.core.custom.parameter.ParamCheck;
import com.zhengqing.mall.model.enums.MiniOmsOrderTabEnum;
import com.zhengqing.mall.model.enums.OmsOrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 商城-订单分页列表-查询参数 </p>
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
@ApiModel("mini-商城-订单分页列表-查询参数")
public class MiniOmsOrderPageDTO extends BasePageDTO implements ParamCheck {

    @ApiModelProperty("搜索关键字（订单号或商品名称）")
    private String keyWord;

    /**
     * {@link OmsOrderStatusEnum}
     */
    @JsonIgnore
    @ApiModelProperty(value = "订单状态", hidden = true)
    private Byte orderStatus;

    /**
     * {@link MiniOmsOrderTabEnum}
     */
    @ApiModelProperty("tab条件")
    private Byte tabValue;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("数据类型（1:今日订单 2：历史订单）")
    private Integer dataType;

    @Override
    public void checkParam() {
        this.userId = UmsUserContext.getUserId();
        if (this.tabValue != null) {
            MiniOmsOrderTabEnum orderTabEnum = MiniOmsOrderTabEnum.getEnum(this.tabValue);
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
                case AFTER_SALE:
                    throw new MyException("查询售后数据请走单独API！");
                default:
                    break;
            }
        }
    }

}
