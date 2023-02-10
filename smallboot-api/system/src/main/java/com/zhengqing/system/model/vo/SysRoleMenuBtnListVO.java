package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色关联菜单按钮输出参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:58
 */
@Data
@ApiModel("角色关联菜单按钮输出参数")
public class SysRoleMenuBtnListVO {

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @ApiModelProperty(value = "按钮ID")
    private Integer btnId;

    @ApiModelProperty(value = "按钮值")
    private String btnPerm;

}
