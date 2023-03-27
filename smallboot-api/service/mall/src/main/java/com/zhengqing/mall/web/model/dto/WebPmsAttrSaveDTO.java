package com.zhengqing.mall.web.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> 商城-属性key-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/20 17:38
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-属性key-请求参数")
public class WebPmsAttrSaveDTO extends BaseDTO {

    @NotNull(groups = {UpdateGroup.class}, message = "主键id不能为空!")
    @ApiModelProperty("主键ID")
    private String id;

    @NotBlank(message = "属性key名称不能为空!")
    @ApiModelProperty(value = "属性key名称", required = true, example = "颜色")
    private String attrKeyName;

    @Min(value = 1, message = "排序值最小为1")
    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

}
