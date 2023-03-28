package com.zhengqing.mall.model.dto;

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
 * <p> 商城-分类关联商品-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 16:04
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-分类关联商品-保存-提交参数")
public class WebPmsCategorySpuRelationSaveDTO extends BaseDTO {

    @NotBlank(message = "分类id不能为空!")
    @ApiModelProperty(value = "分类id", example = "1", required = true)
    private String categoryId;

    @NotBlank(message = "商品ID不能为空!")
    @ApiModelProperty(value = "商品ID", example = "1", required = true)
    private String spuId;

    @NotNull(message = "排序不能为空!")
    @ApiModelProperty(value = "排序", example = "1", required = true)
    private Integer sort;

    @NotNull(message = "是否显示不能为空!")
    @ApiModelProperty(value = "是否显示(false->否,true->是)", example = "true", required = true)
    private Boolean isShow;

    @NotNull(message = "是否上架不能为空!")
    @ApiModelProperty(value = "是否上架(false->下架,true->上架)", example = "true", required = true)
    private Boolean isPut;

}
