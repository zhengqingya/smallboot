package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 保存角色信息传入参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 15:01
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("保存角色信息传入参数")
public class SysRoleSaveDTO extends BaseDTO {

    @NotNull(message = "角色id不能为空！", groups = UpdateGroup.class)
    @ApiModelProperty(value = "主键ID")
    private Integer roleId;

    @NotBlank(message = "角色名不能为空！")
    @ApiModelProperty(value = "角色名")
    private String name;

    @NotBlank(message = "角色编码不能为空！")
    @ApiModelProperty(value = "角色编码")
    private String code;

    @ApiModelProperty(value = "状态(1:开启 0:禁用)")
    private Integer status;

    @ApiModelProperty(value = "是否固定(false->否 true->是)")
    private Boolean isFixed;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
