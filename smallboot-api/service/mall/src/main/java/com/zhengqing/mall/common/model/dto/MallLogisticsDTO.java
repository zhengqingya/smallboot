package com.zhengqing.mall.common.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.mall.common.model.enums.TpsLogisticsCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 第三方-通用物流-查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/23 9:19 下午
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("第三方-通用物流-查询参数")
public class MallLogisticsDTO extends BaseDTO {

    /**
     * {@link TpsLogisticsCodeEnum }
     */
    @NotBlank
    @ApiModelProperty("快递公司编码")
    private String logisticsCode;

    @NotBlank
    @ApiModelProperty("快递单号")
    private String logisticsNo;

    @NotBlank
    @ApiModelProperty("对应收货人电话")
    private String receiverPhone;

}
