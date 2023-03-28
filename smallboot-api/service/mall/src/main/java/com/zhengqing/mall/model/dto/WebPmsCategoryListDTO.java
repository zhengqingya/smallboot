package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 商城-分类-列表-请求参数 </p>
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
@ApiModel("web-商城-分类-列表-请求参数")
public class WebPmsCategoryListDTO extends BaseDTO {

    @ApiModelProperty(value = "分类名称", example = "高质量人类")
    private String name;

}
