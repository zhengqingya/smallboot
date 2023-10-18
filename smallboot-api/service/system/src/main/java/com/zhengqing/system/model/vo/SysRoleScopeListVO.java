package com.zhengqing.system.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>系统管理-角色&数据权限关联表-列表-响应参数</p>
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
@ApiModel("系统管理-角色&数据权限关联表-列表-响应参数")
public class SysRoleScopeListVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("数据权限ID")
    private Integer scopeId;


    public void handleData() {

    }

}
