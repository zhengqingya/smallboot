package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 系统管理 - 用户三方授权表
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 22:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_user_re_oauth")
@ApiModel("系统管理 - 用户三方授权表")
public class SysOauth extends BaseEntity<SysOauth> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "user_re_oauth_id", type = IdType.AUTO)
    private Integer userReOauthId;

    @ApiModelProperty(value = "用户id（关联表`t_sys_user`字段`user_id`）")
    private Integer userId;

    @ApiModelProperty(value = "三方id")
    private String openId;

    @ApiModelProperty(value = "第三方授权类型")
    private Integer oauthType;

}
