package com.zhengqing.common.db.config.mybatis.data.permission.second;

import com.zhengqing.common.db.enums.DataPermissionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * <p> 用户权限信息 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/1/10 17:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionInfo {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty("角色ids")
    private Set<String> roleIdList;

    @ApiModelProperty("自定义权限过滤sql")
    private String sql;

    @NotNull
    @ApiModelProperty("数据权限类型")
    private DataPermissionTypeEnum dataPermissionTypeEnum;

}
