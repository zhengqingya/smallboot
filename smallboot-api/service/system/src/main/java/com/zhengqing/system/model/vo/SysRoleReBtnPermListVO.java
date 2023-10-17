package com.zhengqing.system.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>
 * 角色 -- url/btn权限
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 22:03
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SysRoleReBtnPermListVO extends BaseVO {

    @ApiModelProperty(value = "权限描述")
    private String name;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @ApiModelProperty(value = "url权限标识")
    private String path;

    @ApiModelProperty(value = "按钮权限标识")
    private String btnPerm;

    @ApiModelProperty(value = "角色编码")
    private List<String> roleCodeList;

}
