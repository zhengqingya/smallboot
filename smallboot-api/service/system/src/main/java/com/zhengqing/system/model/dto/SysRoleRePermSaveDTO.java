package com.zhengqing.system.model.dto;

import com.zhengqing.common.core.custom.parameter.HandleParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 保存角色权限信息参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 15:00
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("保存角色权限信息参数")
public class SysRoleRePermSaveDTO extends SysMenuPermBaseDTO implements HandleParam {

    @NotNull(message = "角色id不能为空！")
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @Override
    public void handleParam() {
        super.handleParam();
    }

}
