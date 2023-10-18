package com.zhengqing.system.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p> 菜单权限树 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:54
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuTreeDTO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("排除指定菜单id下级的数据")
    private Integer excludeMenuId;

    @ApiModelProperty("角色ids")
    private List<Integer> roleIdList;

    @ApiModelProperty("菜单ids")
    private List<Integer> menuIdList;

    @ApiModelProperty("是否仅显示带权限的数据  默认false:显示租户/角色下所有权限 true/null:只显示角色有的权限（适用于用户获取角色权限）")
    private Boolean isOnlyShowPerm;

    @ApiModelProperty("父id")
    private Integer parentId;

    /**
     * {@link com.zhengqing.system.enums.SysMenuTypeEnum}
     */
    @ApiModelProperty(value = "类型")
    private Integer type;

}
