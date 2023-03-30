package com.zhengqing.system.model.vo;

import com.zhengqing.system.model.bo.SysMenuTree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>
 * 角色具体权限输出内容(带菜单+按钮+所拥有的权限信息)
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/11 16:04
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("角色具体权限输出内容(带菜单+按钮+所拥有的权限信息)")
public class SysRoleAllPermissionDetailVO {

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "角色编号")
    private String code;

    @ApiModelProperty(value = "状态(1:开启 0:禁用)")
    private Integer status;

    @ApiModelProperty(value = "菜单树(含拥有的按钮权限)")
    private List<SysMenuTree> menuTree;

}
