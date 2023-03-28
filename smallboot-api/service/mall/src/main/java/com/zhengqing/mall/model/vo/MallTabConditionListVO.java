package com.zhengqing.mall.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 商城-tab条件列表-展示视图 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/26 15:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商城-tab条件列表-展示视图")
public class MallTabConditionListVO {

    @ApiModelProperty("值(-1：全部)")
    private Byte value;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("是否单独处理售后订单数据 -- 只在mini端处理")
    private Boolean isAfterSale;

}
