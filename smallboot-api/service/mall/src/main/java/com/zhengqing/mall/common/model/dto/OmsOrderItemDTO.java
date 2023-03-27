package com.zhengqing.mall.common.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p> 商城-订单详情-查询参数 </p>
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
@ApiModel("商城-订单详情-查询参数")
public class OmsOrderItemDTO extends BaseDTO {

    @ApiModelProperty("搜索关键字（订单号或商品名称）")
    private String keyWord;

    @ApiModelProperty("订单号")
    private List<String> orderNoList;

    @ApiModelProperty("订单商品详情ids")
    private List<String> orderItemIdList;

}
