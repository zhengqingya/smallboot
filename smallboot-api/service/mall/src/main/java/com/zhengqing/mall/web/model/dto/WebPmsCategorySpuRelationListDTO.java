package com.zhengqing.mall.web.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 商城-分类关联商品-分页列表-请求参数 </p>
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
@ApiModel("商城-分类关联商品-分页列表-请求参数")
public class WebPmsCategorySpuRelationListDTO extends BaseDTO {

    @ApiModelProperty("分类id")
    private String categoryId;

}
