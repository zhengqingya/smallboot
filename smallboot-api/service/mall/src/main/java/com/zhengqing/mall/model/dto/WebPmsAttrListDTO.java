package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p> 商城-属性key-列表-请求参数 </p>
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
@ApiModel("web-商城-属性key-列表-请求参数")
public class WebPmsAttrListDTO extends BaseDTO {

    @ApiModelProperty("属性key名称")
    private String attrKeyName;

    @ApiModelProperty("ids")
    private List<Long> idList;

}
