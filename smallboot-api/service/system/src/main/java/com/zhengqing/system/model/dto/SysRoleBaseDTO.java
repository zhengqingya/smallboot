package com.zhengqing.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统管理 - 角色管理查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 16:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色管理查询参数")
public class SysRoleBaseDTO {

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty("排除指定角色id下级的数据")
    private Integer excludeRoleId;

}
