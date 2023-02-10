package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 系统管理 - 菜单表
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("系统管理 - 菜单表")
@TableName("t_sys_menu")
public class SysMenu extends IsDeletedBaseEntity<SysMenu> {

    @ApiModelProperty(value = "菜单ID")
    @TableId(type = IdType.AUTO)
    private Integer menuId;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "菜单名称 - 英文")
    private String name;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单链接url")
    private String path;

    @ApiModelProperty(value = "父类菜单ID")
    private Integer parentId;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    @ApiModelProperty(value = "组件名")
    private String component;

    @ApiModelProperty(value = "是否隐藏 true:隐藏 false:显示")
    private Boolean hidden;

    @ApiModelProperty(value = "重定向路径")
    private String redirect;

    @ApiModelProperty(value = "菜单状态 1：启用  0：禁用")
    private Integer status;

    @ApiModelProperty(value = "菜单类型 0菜单 1按钮")
    private Integer type;

    @ApiModelProperty(value = "是否总是显示 0:不显示 1:显示")
    private Integer alwaysShow;

    @ApiModelProperty(value = "面包屑")
    private Boolean breadcrumb;

}
