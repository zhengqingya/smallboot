package com.zhengqing.system.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 角色 关联 btn/url权限 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/3/29 16:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleRePermBO {

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("权限ID")
    private Integer permissionId;

}
