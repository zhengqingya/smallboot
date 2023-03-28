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
 * <p> 商城-商品规格-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-商品规格-请求参数")
public class WebPmsSkuListDTO extends BaseDTO {

    @ApiModelProperty("规格编码")
    private String code;

}
