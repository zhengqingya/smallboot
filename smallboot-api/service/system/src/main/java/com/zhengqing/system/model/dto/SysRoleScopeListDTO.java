package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p> 系统管理-角色&数据权限关联表-列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/18 14:03
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-角色&数据权限关联表-列表-请求参数")
public class SysRoleScopeListDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("数据权限ID")
    private Integer scopeId;

    @ApiModelProperty("创建人")
    private Long createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改人")
    private Long updateBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

}
