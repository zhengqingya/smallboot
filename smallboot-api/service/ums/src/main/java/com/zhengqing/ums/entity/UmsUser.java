package com.zhengqing.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 用户
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
@ApiModel
@TableName("ums_user")
public class UmsUser extends IsDeletedBaseEntity<UmsUser> {

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("openid")
    private String openid;

    @ApiModelProperty("unionid")
    private String unionid;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("手机号码")
    private String phone;

    /**
     * {@link UserSexEnum}
     */
    @ApiModelProperty("性别")
    private Byte sex;

    @ApiModelProperty("生日")
    private String birthday;

    @ApiModelProperty("头像")
    private String avatarUrl;

    /**
     * {@link com.zhengqing.ums.enums.MiniTypeEnum}
     */
    @ApiModelProperty("小程序类型")
    private Integer type;

    @ApiModelProperty("小程序appId")
    private String appId;

}
