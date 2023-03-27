package com.zhengqing.mall.mini.model.vo;

import com.zhengqing.mall.common.model.vo.OmsOrderAfterSaleBaseVO;
import com.zhengqing.mall.common.model.vo.OmsOrderItemVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
@ApiModel("mini-商城-售后列表base-展示视图")
public class MiniOmsOrderAfterSaleBaseVO extends OmsOrderAfterSaleBaseVO {

    @ApiModelProperty("关联商品信息")
    private List<OmsOrderItemVO> spuList;

    @Override
    public void handleData() {
        super.handleData();
    }

}
