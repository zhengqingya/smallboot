package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> mini-商品-预售提醒-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/8 14:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商品-预售提醒-请求参数")
public class MiniOmsOrderUnPayDTO extends BaseDTO {

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("租户id")
    private Integer tenantId;

}
