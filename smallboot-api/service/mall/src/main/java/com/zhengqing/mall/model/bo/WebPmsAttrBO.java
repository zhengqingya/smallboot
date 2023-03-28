package com.zhengqing.mall.model.bo;


import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 商城-属性
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
@ApiModel("web-商城-属性")
public class WebPmsAttrBO extends BaseBO {

    @NotNull(message = "属性key-id不能为空！")
    @ApiModelProperty(value = "属性key-ID", required = true, example = "1")
    private String attrKeyId;

    @NotBlank(message = "属性key名称不能为空!")
    @ApiModelProperty(value = "属性key名称", required = true, example = "颜色")
    private String attrKeyName;

    @Valid
    @NotEmpty(message = "属性值不能为空！")
    @ApiModelProperty(value = "属性值", required = true)
    private List<AttrValueItem> attrValueList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("商城-属性value")
    public static class AttrValueItem {
        @NotNull(message = "属性value-id不能为空!")
        @ApiModelProperty(value = "属性value-id", required = true, example = "1")
        private String attrValueId;

        @NotBlank(message = "属性value-名称不能为空!")
        @ApiModelProperty(value = "属性value-名称", required = true, example = "蓝色")
        private String attrValueName;
    }

}
