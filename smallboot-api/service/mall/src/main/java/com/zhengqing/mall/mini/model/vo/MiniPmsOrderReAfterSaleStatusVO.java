package com.zhengqing.mall.mini.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * <p> 商城-订单-售后状态-展示视图 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 11:05
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商城-订单-售后状态-展示视图")
public class MiniPmsOrderReAfterSaleStatusVO extends BaseVO {

    @ApiModelProperty(value = "订单-售后处理截止时间")
    private Date afterSaleEndTime;

    @ApiModelProperty(value = "商品是否可售后")
    private List<MiniOmsReSpuAfterSaleStatusVO> spuList;


}
