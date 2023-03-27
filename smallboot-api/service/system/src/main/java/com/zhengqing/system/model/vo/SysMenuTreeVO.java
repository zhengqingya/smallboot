package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 用户菜单权限树
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:54
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SysMenuTreeVO {

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "菜单名称 - 英文")
    private String name;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单链接")
    private String path;

    @ApiModelProperty(value = "上级菜单ID")
    private Integer parentId;

    @ApiModelProperty(value = "上级菜单名")
    private String parentName;

    @ApiModelProperty(value = "显示顺序")
    private Integer sort;

    @ApiModelProperty(value = "组件名")
    private String component;

    @ApiModelProperty(value = "是否隐藏 true:隐藏 false:显示")
    private Boolean hidden;

    @ApiModelProperty(value = "重定向url")
    private String redirect;

    @ApiModelProperty(value = "面包屑是否显示")
    private Boolean breadcrumb;

    @ApiModelProperty(value = "是否显示子菜单(true:显示 false:隐藏)")
    private Boolean isShowChildren;

    @ApiModelProperty(value = "下级菜单")
    private List<SysMenuTreeVO> children = new LinkedList<>();

    @ApiModelProperty(value = "按钮权限")
    private SysUserBtnVO meta;

    // 下面属性只在角色管理页面-权限中使用

    @ApiModelProperty(value = "菜单关联的所有按钮信息")
    private List<SysMenuReBtnPermListVO> btnInfoList;

    @ApiModelProperty(value = "角色关联菜单下的所拥有的按钮权限信息")
    private List<Integer> permissionIdList;

}
