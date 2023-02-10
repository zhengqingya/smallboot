package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统管理-菜单关联权限表
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@TableName("t_sys_permission")
public class SysPermission extends BaseEntity<SysPermission> {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @ApiModelProperty(value = "按钮权限标识")
    private String btnPerm;

    @ApiModelProperty(value = "URL权限标识")
    private String urlPerm;

}
