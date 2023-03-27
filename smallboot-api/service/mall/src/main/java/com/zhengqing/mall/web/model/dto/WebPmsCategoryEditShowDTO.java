package com.zhengqing.mall.web.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p> 商城-分类显示-保存参数 </p>
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
@ApiModel("web-商城-分类显示-保存参数")
public class WebPmsCategoryEditShowDTO extends BaseDTO {

    @NotEmpty(message = "商品不能为空")
    @ApiModelProperty(value = "商品ids", required = true, example = "[1]")
    private List<String> idList;

    @NotNull(message = "是否显示不能为空！")
    @ApiModelProperty(value = "是否显示：false->隐藏；true->显示", required = true, example = "true")
    private Boolean isShow;

}
