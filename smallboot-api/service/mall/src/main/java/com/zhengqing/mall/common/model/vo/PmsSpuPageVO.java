package com.zhengqing.mall.common.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>商城-商品分页列表-展示视图</p>
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
@ApiModel("商城-商品分页列表-展示视图")
public class PmsSpuPageVO extends PmsSpuBaseVO {

    @ApiModelProperty("ID")
    private String spuId;

    @Override
    public void handleData(List<?> skuList) {
        super.handleData(skuList);
    }


}
