package com.zhengqing.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 保存用户角色参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 14:19
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("保存用户角色参数")
public class SysUserRoleSaveDTO {

    @NotNull(message = "用户id不能为空！")
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("角色ids")
    private List<Integer> roleIdList;

}
