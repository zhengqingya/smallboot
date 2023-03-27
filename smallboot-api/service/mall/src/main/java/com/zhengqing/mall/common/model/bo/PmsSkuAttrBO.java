package com.zhengqing.mall.common.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
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
 * <p>
 * 商城-规格-属性
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/22 2:27 下午
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-规格-属性")
public class PmsSkuAttrBO extends BaseBO {

    @NotNull(message = "属性key-id不能为空！")
    @ApiModelProperty(value = "属性key-ID", required = true, example = "1")
    private String attrKeyId;

    @NotBlank(message = "属性key名称不能为空!")
    @ApiModelProperty(value = "属性key名称", required = true, example = "颜色")
    private String attrKeyName;

    @NotNull(message = "属性value-id不能为空!")
    @ApiModelProperty(value = "属性value-id", required = true, example = "1")
    private String attrValueId;

    @NotBlank(message = "属性value-名称不能为空!")
    @ApiModelProperty(value = "属性value-名称", required = true, example = "蓝色")
    private String attrValueName;

}
