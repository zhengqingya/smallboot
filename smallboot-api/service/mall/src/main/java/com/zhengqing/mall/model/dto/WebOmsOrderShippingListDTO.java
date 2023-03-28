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
 * <p> 商城-订单配送表-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:56
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-订单配送表-分页列表-请求参数")
public class WebOmsOrderShippingListDTO extends BaseDTO {

    @ApiModelProperty("xxx")
    private String xxx;

}
