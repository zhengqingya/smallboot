package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.common.db.config.mybatis.handler.ListJsonIntegerTypeHandler;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * <p>
 * 系统管理-用户基础信息表
 * </p>
 *
 * @author zhengqingya
 * @date 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("系统管理-用户基础信息表")
@TableName(value = "t_sys_user", autoResultMap = true)
public class SysUser extends IsDeletedBaseEntity<SysUser> {

    @ApiModelProperty(value = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别")
    @TableField(value = "sex")
    private UserSexEnum sexEnum;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    /**
     * 值为空时，MP更新数据库时不忽略此字段值
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @ApiModelProperty(value = "岗位ids")
    @TableField(typeHandler = ListJsonIntegerTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private List<Integer> postIdList;

    @ApiModelProperty(value = "是否固定(false->否 true->是)")
    private Boolean isFixed;

    /**
     * 值为空时，MP更新数据库时不忽略此字段值
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "小程序用户id")
    private Long miniUserId;

}
