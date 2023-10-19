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
import java.util.List;

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

    @ApiModelProperty("自定义ID（前端表格树使用）")
    private String customId;

    @ApiModelProperty("自定义名称（前端表格树使用）")
    private String customName;

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("菜单ID")
    private Integer menuId;

    @ApiModelProperty("菜单全路径名称( eg: /系统管理/用户管理 )")
    private String menuFullName;

    @ApiModelProperty("权限名称")
    private String scopeName;

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

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("子级")
    private List<SysScopeDataBaseVO> children;

    public void handleData() {

    }

}
