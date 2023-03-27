package com.zhengqing.system.model.dto;

import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 菜单按钮保存参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 21:00
 */
@Data
@ApiModel("菜单按钮保存参数")
public class SysMenuReBtnPermSaveDTO {

    @ApiModelProperty(value = "id")
    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空!")
    private Integer id;

    @NotBlank(message = "名称不能为空！")
    @ApiModelProperty(value = "名称")
    private String name;

    @NotNull(message = "菜单id")
    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @NotBlank(message = "按钮权限标识不能为空！")
    @ApiModelProperty(value = "按钮权限标识")
    private String btnPerm;

    @NotBlank(message = "URL权限标识不能为空！")
    @ApiModelProperty(value = "URL权限标识")
    private String urlPerm;

}
