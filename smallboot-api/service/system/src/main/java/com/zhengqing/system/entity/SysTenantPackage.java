package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.config.mybatis.handler.ListJsonIntegerTypeHandler;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
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
public class SysTenantPackage extends IsDeletedBaseEntity<SysTenantPackage> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("套餐名")
    private String name;

    @ApiModelProperty("状态(1:开启 0:禁用)")
    private Integer status;

    @ApiModelProperty("关联的菜单ids")
    @TableField(typeHandler = ListJsonIntegerTypeHandler.class)
    private List<Integer> menuIdList;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("排序")
    private Integer sort;

}
