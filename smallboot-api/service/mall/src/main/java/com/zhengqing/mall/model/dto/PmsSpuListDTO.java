package com.zhengqing.mall.model.dto;

import com.zhengqing.common.core.custom.parameter.CheckParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 商城-商品列表-查询参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商城-商品列表-查询参数")
public class PmsSpuListDTO implements CheckParam {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty(value = "分类ID", example = "1")
    private String notEqCategoryId;

    @Override
    public void checkParam() {

    }

}
