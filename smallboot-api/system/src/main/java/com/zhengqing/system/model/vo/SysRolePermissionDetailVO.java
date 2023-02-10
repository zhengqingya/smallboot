package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>
 * 角色权限输出内容
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 14:48
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("角色权限输出内容")
public class SysRolePermissionDetailVO {

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "角色编号")
    private String code;

    @ApiModelProperty(value = "状态(1:开启 0:禁用)")
    private Integer status;

    @ApiModelProperty(value = "角色可访问的菜单ids")
    private List<Integer> menuIdList;

}
