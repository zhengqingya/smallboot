package com.zhengqing.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 菜单 关联 url/btn权限
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
public class SysMenuRePermListVO extends BaseVO {

    @JsonIgnore
    @ApiModelProperty(value = "菜单ID", hidden = true)
    private Integer menuId;

    // 下面是菜单对应的权限

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("权限描述")
    private String name;

    @ApiModelProperty("url权限标识")
    private String urlPerm;

    @ApiModelProperty("按钮权限标识")
    private String btnPerm;
 
}
