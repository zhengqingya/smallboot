package com.zhengqing.system.model.dto;

import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 菜单保存参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:55
 */
@Data
@ApiModel
public class SysMenuSaveDTO {

    @NotNull(groups = {UpdateGroup.class}, message = "ID不能为空!")
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @NotNull(message = "父ID不能为空！")
    @ApiModelProperty(value = "父ID")
    private Integer parentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "访问路径")
    private String path;

    @ApiModelProperty(value = "按钮权限标识")
    private String btnPerm;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "组件名")
    private String component;

    @ApiModelProperty(value = "重定向路径")
    private String redirect;

    @ApiModelProperty(value = "是否显示(1:显示 0:隐藏)")
    private Boolean isShow;

    @ApiModelProperty(value = "是否显示面包屑(1:显示 0:隐藏)")
    private Boolean isShowBreadcrumb;

    /**
     * {@link com.zhengqing.system.enums.SysMenuTypeEnum}
     */
    @NotNull(message = "类型不能为空！")
    @ApiModelProperty(value = "类型")
    private Integer type;

}
