package com.zhengqing.system.model.dto;

import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 保存角色菜单权限信息传入参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/14 11:15
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("保存角色菜单权限信息传入参数")
public class SysRoleMenuSaveDTO {

    @NotNull(message = "角色id不能为空！", groups = UpdateGroup.class)
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "角色可访问的菜单ids")
    private List<Integer> menuIdList;

}
