package com.zhengqing.system.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p>系统管理-数据权限-base-响应参数</p>
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
@ApiModel("系统管理-数据权限-base-响应参数")
public class SysScopeDataBaseVO extends BaseVO {

    @ApiModelProperty("主键ID")
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

    @ApiModelProperty("创建时间")
    private Date createTime;

    public void handleData() {

    }

}
