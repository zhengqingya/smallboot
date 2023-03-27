package com.zhengqing.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户角色关联表查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:55
 */
@Data
@ApiModel("用户角色关联表查询参数")
public class SysUserRoleListDTO {

    @ApiModelProperty(value = "id")
    private String id;

}
