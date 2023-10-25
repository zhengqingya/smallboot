package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p>  系统管理-租户信息 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 15:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_tenant")
@ApiModel("系统管理-租户信息")
public class SysTenant extends BaseEntity<SysTenant> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("租户名")
    private String name;

    @ApiModelProperty("管理员用户id")
    private Integer adminUserId;

    @ApiModelProperty("管理员")
    private String adminName;

    @ApiModelProperty("管理员手机号")
    private String adminPhone;

    @ApiModelProperty("租户状态(0->停用 1->开启)")
    private Integer status;

    @ApiModelProperty("过期时间")
    private Date expireTime;

    @ApiModelProperty("账号数量")
    private Integer accountCount;

    @ApiModelProperty("租户套餐ID")
    private Integer packageId;

    @ApiModelProperty("排序")
    private Integer sort;

}
