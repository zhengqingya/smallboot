package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.config.mybatis.handler.ListIntegerTypeHandler;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * <p>  系统管理-租户套餐 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 10:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_sys_tenant_package", autoResultMap = true)
@ApiModel("系统管理-租户套餐")
public class SysTenantPackage extends BaseEntity<SysTenantPackage> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("套餐名")
    private String name;

    @ApiModelProperty("状态(1:开启 0:禁用)")
    private Integer status;

    @ApiModelProperty("关联的菜单ids")
    @TableField(typeHandler = ListIntegerTypeHandler.class)
    private List<Integer> menuIdList;

    @ApiModelProperty("关联的按钮权限ids")
    @TableField(typeHandler = ListIntegerTypeHandler.class)
    private List<Integer> permissionIdList;

    @ApiModelProperty("备注")
    private String remark;

}