package com.zhengqing.mall.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> 商城-通用字典参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/28 9:40 下午
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-通用字典参数")
public class MallDictBO extends BaseBO {

    @NotBlank(message = "类型编码不能为空！")
    @ApiModelProperty(value = "类型编码", required = true, example = "btn")
    private String code;

    @NotBlank(message = "字典名不能为空！")
    @ApiModelProperty(value = "字典名(展示用)", required = true, example = "添加")
    private String name;

    @NotBlank(message = "字典值不能为空！")
    @ApiModelProperty(value = "字典值", required = true, example = "add")
    private String value;

    @NotNull(message = "展示排序不能为空！")
    @ApiModelProperty(value = "展示排序", required = true, example = "1")
    private Integer sort;

    @ApiModelProperty(value = "备注", required = true, example = "this is the add.")
    private String remark;

}
