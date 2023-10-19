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

/**
 * <p> 系统管理-数据权限-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/18 14:00
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-数据权限-保存-提交参数")
public class SysScopeDataSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @NotNull(message = "菜单ID不能为空！")
    @ApiModelProperty("菜单ID")
    private Integer menuId;

    @NotBlank(message = "权限名称不能为空！")
    @ApiModelProperty("权限名称")
    private String scopeName;

    @NotBlank(message = "权限字段不能为空！")
    @ApiModelProperty("权限字段")
    private String scopeColumn;

    @NotBlank(message = "可见字段不能为空！")
    @ApiModelProperty("可见字段")
    private String scopeVisibleField;

    @NotBlank(message = "全权限类名不能为空！")
    @ApiModelProperty("全权限类名")
    private String scopeClass;

    /**
     * {@link com.zhengqing.common.db.enums.DataPermissionTypeEnum}
     */
    @NotNull(message = "规则类型不能为空！")
    @ApiModelProperty("规则类型")
    private Integer scopeType;

    @ApiModelProperty("规则值")
    private String scopeValue;

    @ApiModelProperty("备注")
    private String remark;


}
