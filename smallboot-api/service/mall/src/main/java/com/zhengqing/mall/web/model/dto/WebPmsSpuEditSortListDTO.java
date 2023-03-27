package com.zhengqing.mall.web.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p> 商城-商品排序-保存参数 </p>
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
@ApiModel("web-商城-商品排序-保存参数")
public class WebPmsSpuEditSortListDTO extends BaseDTO {

    @NotNull(message = "商品不能为空")
    @ApiModelProperty(value = "商品id", required = true, example = "1")
    private String id;

    @Min(value = 0, message = "排序值最小为0")
    @NotNull(message = "排序值不能为空")
    @ApiModelProperty(value = "排序值", required = true, example = "1")
    private Integer sort;

}
