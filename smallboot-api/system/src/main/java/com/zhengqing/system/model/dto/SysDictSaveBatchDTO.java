package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
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
 * <p>
 * 数据字典批量保存参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:55
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("数据字典批量保存参数")
public class SysDictSaveBatchDTO extends BaseDTO {

    @ApiModelProperty(value = "字典类型id(关联`t_sys_dict_type`表`id`字段)")
    private Integer dictTypeId;

    @ApiModelProperty(value = "字典类型名称(暂只做新增数据时可用)", required = true, example = "按钮权限")
    private String dictTypeName;

    @NotBlank(message = "字典类型id不能为空!")
    @ApiModelProperty(value = "字典类型编码", example = "permission_btn")
    private String code;

    @ApiModelProperty(value = "字典id", example = "1")
    private Integer id;

    @NotBlank(message = "字典名称不能为空!")
    @ApiModelProperty(value = "名称", required = true, example = "新增")
    private String name;

    @NotBlank(message = "字典值不能为空!")
    @ApiModelProperty(value = "值", required = true, example = "add")
    private String value;

    @NotNull(message = "字典值不能为空!")
    @ApiModelProperty(value = "状态(0->停用 1->正常)", required = true, example = "1")
    private Integer status;

    @NotNull(message = "展示顺序不能为空!")
    @ApiModelProperty(value = "排序", required = true, example = "1")
    private Integer sort;

    @ApiModelProperty(value = "备注", example = "this is the add.")
    private String remark;

}
