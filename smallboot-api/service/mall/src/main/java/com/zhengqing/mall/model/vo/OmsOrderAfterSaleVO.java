package com.zhengqing.mall.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>商城-订单是否售后-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-订单是否售后-响应参数")
public class OmsOrderAfterSaleVO extends BaseVO {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("是否售后 -- 售后中/售后完成 (true->是 false->否)")
    private Boolean isAfterSale;

}
