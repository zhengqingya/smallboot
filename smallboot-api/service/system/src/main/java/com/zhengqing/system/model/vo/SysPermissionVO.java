package com.zhengqing.system.model.vo;

import com.zhengqing.system.model.bo.SysMenuTree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 系统管理 - 角色+菜单+所拥有按钮权限 输出内容
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 16:19
 */
@Data
@ApiModel("系统管理 - 角色+菜单+所拥有按钮权限 输出内容")
public class SysPermissionVO {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "角色名")
    private String roleNames;

    @ApiModelProperty(value = "角色编号")
    private String roleCodes;

    @ApiModelProperty(value = "角色ID")
    private List<Integer> roleIdList;

    @ApiModelProperty(value = "角色")
    private List<SysRoleListVO> roleList;

    @ApiModelProperty(value = "角色可访问的菜单ID")
    private List<Integer> menuIdList;

    @ApiModelProperty(value = "菜单树(含拥有的按钮权限)")
    private List<SysMenuTree> menuAndBtnPermissionTree;

}
