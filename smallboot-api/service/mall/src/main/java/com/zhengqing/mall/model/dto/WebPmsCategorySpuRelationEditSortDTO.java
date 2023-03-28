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
 * <p> 商城-分类关联商品排序-保存参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/20 16:11
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-分类关联商品排序-保存参数")
public class WebPmsCategorySpuRelationEditSortDTO extends BaseDTO {

    @NotBlank(message = "分类关联商品id不能为空")
    @ApiModelProperty(value = "分类关联商品id", required = true, example = "1")
    private String id;

    @NotNull(message = "排序值不能为空！")
    @ApiModelProperty(value = "排序值", required = true, example = "1")
    private Integer sort;

}
