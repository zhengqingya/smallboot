package com.zhengqing.system.model.dto;

import com.zhengqing.common.core.custom.validator.common.CreateGroup;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.common.core.custom.fieldrepeat.FieldRepeatValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 数据字典类型保存参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数据字典类型保存参数")
@FieldRepeatValidator(tableName = "t_sys_dict_type", fieldNames = {"code"}, dbFieldNames = {"code"},
        message = "字典类型编码重复，请重新输入！")
public class SysDictTypeSaveDTO {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空!")
    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;

    @NotBlank(groups = {CreateGroup.class}, message = "字典类型编码不能为空!")
    @ApiModelProperty(value = "字典类型编码-新增时才有用")
    private String code;

    @NotBlank(message = "字典类型名称不能为空!")
    @ApiModelProperty(value = "字典类型名称(展示用)", required = true, example = "权限按钮")
    private String name;

    @NotNull(message = "请选择状态!")
    @ApiModelProperty(value = "状态(0->停用 1->正常)", required = true, example = "1")
    private Integer status;

}
