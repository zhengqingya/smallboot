package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  系统管理-数据权限 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/18 14:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_scope_data")
@ApiModel("系统管理-数据权限")
public class SysScopeData extends IsDeletedBaseEntity<SysScopeData> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("菜单ID")
    private Integer menuId;

    @ApiModelProperty("权限名称")
    private String scopeName;

    @ApiModelProperty("权限字段")
    private String scopeColumn;

    @ApiModelProperty("可见字段")
    private String scopeVisibleField;

    @ApiModelProperty("权限类名")
    private String scopeClass;

    /**
     * {@link com.zhengqing.common.db.enums.DataPermissionTypeEnum}
     */
    @ApiModelProperty("规则类型")
    private Integer scopeType;

    @ApiModelProperty("规则值")
    private String scopeValue;

    @ApiModelProperty("备注")
    private String remark;

}
