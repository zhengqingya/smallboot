package com.zhengqing.mall.mini.model.vo;

import com.zhengqing.mall.common.model.bo.PmsSkuBO;
import com.zhengqing.mall.common.model.vo.PmsSpuDetailVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>商城-商品-详情-展示视图</p>
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
@ApiModel("mini-商城-商品-详情-展示视图")
public class MiniPmsSpuDetailVO extends PmsSpuDetailVO {

    @ApiModelProperty("规格明细列表")
    private List<PmsSkuBO> skuList;

    @Override
    public void handleData(List<?> skuList) {
        super.handleData(this.skuList);
    }

}
