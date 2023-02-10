package com.zhengqing.system.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 数据字典类型参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/28 4:54 上午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("数据字典类型参数")
public class SysDictTypeBO {

    @ApiModelProperty(value = "字典id")
    private Integer id;

    @ApiModelProperty(value = "字典类型编码")
    private String code;

}
