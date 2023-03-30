package com.zhengqing.system.model.bo;

import com.zhengqing.system.model.vo.SysRoleRePermVO;
import com.zhengqing.system.model.vo.SysUserBtnVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>
 * 菜单权限树
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
public class SysMenuTree {

    // ==================== ↓↓↓↓↓↓ 菜单基础信息 ↓↓↓↓↓↓ ====================

    @ApiModelProperty("菜单ID")
    private Integer menuId;

    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("菜单名称 - 英文")
    private String name;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("菜单链接")
    private String path;

    @ApiModelProperty("上级菜单ID")
    private Integer parentId;

    @ApiModelProperty("上级菜单名")
    private String parentName;

    @ApiModelProperty("显示顺序")
    private Integer sort;

    @ApiModelProperty("组件名")
    private String component;

    @ApiModelProperty("是否隐藏 true:隐藏 false:显示")
    private Boolean hidden;

    @ApiModelProperty("重定向url")
    private String redirect;

    @ApiModelProperty("面包屑是否显示")
    private Boolean breadcrumb;

    @ApiModelProperty("是否显示子菜单(true:显示 false:隐藏)")
    private Boolean isShowChildren;

    @ApiModelProperty("下级菜单")
    private List<SysMenuTree> children;

    // ==================== ↓↓↓↓↓↓ 权限信息 ↓↓↓↓↓↓ ====================

    @ApiModelProperty("是否具有菜单权限")
    private Boolean isHasPerm;

    @ApiModelProperty("按钮权限")
    private List<SysRoleRePermVO> permList;

    @ApiModelProperty("按钮权限")
    private SysUserBtnVO meta;

}
