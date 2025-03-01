package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zhengqing.common.base.enums.SysRoleCodeEnum;
import com.zhengqing.common.core.custom.fieldrepeat.FieldRepeatValidator;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 系统管理-角色管理
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 15:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("角色管理")
@TableName("t_sys_role")
@FieldRepeatValidator(tableName = "t_sys_role", idDbName = "role_id", fieldNames = "code", dbFieldNames = {"code"},
        message = "角色编码重复，请重新输入！")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysRole extends BaseEntity<SysRole> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("父id")
    private Integer parentId;

    @ApiModelProperty(value = "角色名")
    private String name;

    /**
     * {@link SysRoleCodeEnum}
     */
    @ApiModelProperty(value = "角色编码")
    private String code;

    @ApiModelProperty(value = "状态(1:开启 0:禁用)")
    private Integer status;

    @ApiModelProperty(value = "是否固定(false->否 true->是)")
    private Boolean isFixed;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否刷新所有租户权限数据(false->否 true->是)")
    private Boolean isRefreshAllTenant;

}
