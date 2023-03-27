package com.zhengqing.mall.mini.model.vo;

import com.zhengqing.mall.common.model.vo.OmsOrderItemVO;
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
 * <p>商城-订单分页列表-展示视图</p>
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
@ApiModel("mini-商城-订单分页列表-展示视图")
public class MiniOmsOrderPageVO extends MiniOmsOrderBaseVO {

    @ApiModelProperty("下单时间")
    private Date createTime;

    @ApiModelProperty("支付时间")
    private Date payTime;

    @ApiModelProperty("商品信息")
    private List<OmsOrderItemVO> spuList;

    @Override
    public void handleData() {
        super.handleData();
    }

}
