package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
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
 * <p> 商城-分类-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 14:01
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-分类-保存-提交参数")
public class WebPmsCategorySaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotBlank(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private String id;

    @NotBlank(message = "父分类id不能为空!")
    @ApiModelProperty(value = "父分类id(0:第一级)", example = "0")
    private String parentId;

    @NotBlank(message = "分类名称不能为空!")
    @ApiModelProperty(value = "分类名称", example = "高质量人类")
    private String name;

    @NotNull(message = "排序不能为空!")
    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @NotNull(message = "是否显示不能为空!")
    @ApiModelProperty(value = "是否显示(false->否,true->是)", example = "true")
    private Boolean isShow;

}
