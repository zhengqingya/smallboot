package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 系统管理-角色表 输出内容
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 16:19
 */
@Data
@ApiModel("系统管理 - 角色表 输出内容")
public class SysRoleBaseVO {

    @ApiModelProperty("父id")
    private Integer parentId;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "角色编码")
    private String code;

    @ApiModelProperty(value = "状态(1:开启 0:禁用)")
    private Integer status;

    @ApiModelProperty(value = "是否固定(false->否 true->是)")
    private Boolean isFixed;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty("子级")
    private List<SysRoleBaseVO> children;

    public void handleData() {

    }

}