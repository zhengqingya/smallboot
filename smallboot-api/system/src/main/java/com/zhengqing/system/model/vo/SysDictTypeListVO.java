package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p> 数据字典类型-响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/15 4:37 下午
 */
@Data
@ApiModel("数据字典类型-响应参数")
public class SysDictTypeListVO {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "字典类型编码")
    private String code;

    @ApiModelProperty(value = "字典类型名称(展示用)")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
