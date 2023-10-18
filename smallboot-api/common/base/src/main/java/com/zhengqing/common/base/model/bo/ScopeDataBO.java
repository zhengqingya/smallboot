package com.zhengqing.common.base.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 数据权限 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 23:16
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ScopeDataBO {
    @ApiModelProperty("权限字段")
    private String scopeColumn;

    @ApiModelProperty("可见字段")
    private String scopeVisibleField;

    @ApiModelProperty("全权限类名")
    private String scopeClass;

    /**
     * {@link com.zhengqing.common.db.enums.DataPermissionTypeEnum}
     */
    @ApiModelProperty("规则类型")
    private Integer scopeType;

    @ApiModelProperty("规则值")
    private String scopeValue;
}