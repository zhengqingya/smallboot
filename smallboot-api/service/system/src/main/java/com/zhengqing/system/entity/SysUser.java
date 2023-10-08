package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@TableName("t_sys_user")
// 对注解分组的排序，可以通脱他判断先后顺序
// @GroupSequence({FieldRepeatValidator.class,NotNull.class, Default.class})
// @JsonIgnoreProperties(ignoreUnknown = true)
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

}
