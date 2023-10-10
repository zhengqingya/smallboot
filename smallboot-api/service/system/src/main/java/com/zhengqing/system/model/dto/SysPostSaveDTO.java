package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
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
 * <p> 系统管理-岗位-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 17:49
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-岗位-保存-提交参数")
public class SysPostSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @NotBlank(message = "岗位名称不能为空！")
    @ApiModelProperty("岗位名称")
    private String name;

    @NotBlank(message = "岗位编码不能为空！")
    @ApiModelProperty("岗位编码")
    private String code;

    @ApiModelProperty("状态(1:正常 0:停用)")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("备注")
    private String remark;

}
