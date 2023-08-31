package com.zhengqing.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p> 保存角色关联按钮权限ids提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 18:48
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("保存角色关联按钮权限ids提交参数")
public class SysRoleRePermIdsSaveDTO {

    @NotNull(message = "角色Id不能为空！")
    @ApiModelProperty("角色Id")
    private Integer roleId;

    @ApiModelProperty("按钮权限ids")
    private List<Integer> permissionIdList;

}
