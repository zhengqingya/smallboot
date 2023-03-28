package com.zhengqing.mall.model.vo;

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
@ApiModel("mini-商城-订单base-展示视图")
public class MiniOmsOrderBaseVO extends OmsOrderBaseVO {

    @ApiModelProperty(value = "售后处理截止时间")
    private Date afterSaleEndTime;

    @Override
    public void handleData() {
        super.handleData();
    }


}
