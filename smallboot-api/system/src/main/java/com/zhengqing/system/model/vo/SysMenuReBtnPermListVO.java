package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 菜单按钮权限展示参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 22:03
 */
@Data
@ApiModel("菜单按钮权限展示参数")
public class SysMenuReBtnPermListVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "按钮权限标识")
    private String btnPerm;

    @ApiModelProperty(value = "URL权限标识")
    private String urlPerm;

}
