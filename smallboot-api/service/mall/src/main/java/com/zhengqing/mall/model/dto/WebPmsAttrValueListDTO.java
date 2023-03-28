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
 * <p> 商城-属性value-分页列表-请求参数 </p>
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
@ApiModel("web-商城-属性value-分页列表-请求参数")
public class WebPmsAttrValueListDTO extends BaseDTO {

    @ApiModelProperty("属性key")
    private String attrKeyId;

    @ApiModelProperty("属性值名")
    private String attrValueName;

}
