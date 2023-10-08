package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p> 系统管理-租户套餐-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 10:34
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-租户套餐-保存-提交参数")
public class SysTenantPackageSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @NotBlank(message = "套餐名不能为空！")
    @ApiModelProperty("套餐名")
    private String name;

    @ApiModelProperty("状态(1:开启 0:禁用)")
    private Integer status;

    @ApiModelProperty("关联的菜单ids")
    private List<Integer> menuIdList;

    @ApiModelProperty("备注")
    private String remark;


}
