package com.zhengqing.mall.mini.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>商城-售后列表-展示视图</p>
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
@ApiModel("mini-商城-售后列表-展示视图")
public class MiniOmsOrderAfterSalePageVO extends MiniOmsOrderAfterSaleBaseVO {

    @ApiModelProperty("售后编号")
    private String afterSaleNo;

    @Override
    public void handleData() {
        super.handleData();
    }

}
