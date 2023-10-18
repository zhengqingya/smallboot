package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  系统管理-角色&数据权限关联表 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/18 14:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_role_scope")
@ApiModel("系统管理-角色&数据权限关联表")
public class SysRoleScope extends BaseEntity<SysRoleScope> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("数据权限ID")
    private Integer scopeId;

}
