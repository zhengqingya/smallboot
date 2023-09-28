package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.mall.enums.TpsLogisticsCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * <p> 商城-订单物流查询-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 10:46
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-订单物流查询-提交参数")
public class OmsLogisticDTO extends BaseDTO {

    /**
     * {@link TpsLogisticsCodeEnum }
     */
    @NotBlank
    @ApiModelProperty(value = "快递公司编码", required = true, example = "ZTO")
    private String logisticsCode;

    /**
     * {@link TpsLogisticsCodeEnum }
     */
//    @ApiModelProperty(value = "物流公司", required = true, example = "中通")
//    private String logisticsCompany;

    @NotBlank
    @ApiModelProperty(value = "快递单号", required = true, example = "75814776555001")
    private String logisticsNo;


}
