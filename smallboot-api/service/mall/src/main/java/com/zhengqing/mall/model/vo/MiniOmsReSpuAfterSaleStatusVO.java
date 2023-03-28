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
 * <p> mini-商城-订单-商品售后状态-展示视图 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/12/10 15:01
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商城-订单-商品售后状态-展示视图")
public class MiniOmsReSpuAfterSaleStatusVO extends BaseVO {

    @ApiModelProperty("订单详情ID")
    private String orderItemId;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("是否可售后（true->可售后  false->不可售后）")
    private Boolean isAfterSale;

}
