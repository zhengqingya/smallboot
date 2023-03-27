package com.zhengqing.mall.common.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> 商品-预售-请求参数 </p>
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
@ApiModel("商品-预售-请求参数")
public class PmsSpuPresellDTO extends BaseDTO {

    @NotNull(message = "租户id不能为空！")
    @ApiModelProperty("租户id(mq使用)")
    private Integer tenantId;

    @NotBlank(message = "商品ID不能为空！")
    @ApiModelProperty(value = "商品ID", required = true, example = "1")
    private String spuId;

}
