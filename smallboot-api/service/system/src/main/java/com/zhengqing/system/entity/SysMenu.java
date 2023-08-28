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

    @ApiModelProperty(value = "父类菜单ID")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单访问路径")
    private String path;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "重定向路径")
    private String redirect;

    @ApiModelProperty(value = "是否显示(1:显示 0:隐藏)")
    private Boolean isShow;

    @ApiModelProperty(value = "是否显示面包屑(1:显示 0:隐藏)")
    private Boolean isShowBreadcrumb;

}
