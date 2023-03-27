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
 * <p> 商城-属性value-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/22 15:06
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-属性value-保存-提交参数")
public class WebPmsAttrValueSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private String id;

    @NotNull(message = "属性key不能为空!")
    @ApiModelProperty(value = "属性key", required = true, example = "1")
    private String attrKeyId;

    @NotBlank(message = "属性值不能为空!")
    @ApiModelProperty(value = "属性值", required = true, example = "蓝色")
    private String attrValueName;

    @Min(value = 1, message = "排序值最小为1")
    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;


}
