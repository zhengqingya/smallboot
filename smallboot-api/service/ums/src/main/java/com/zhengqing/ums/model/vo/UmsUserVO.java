package com.zhengqing.ums.model.vo;

import com.zhengqing.common.auth.model.vo.AuthLoginVO;
import com.zhengqing.common.core.enums.UserSexEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 10:48
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UmsUserVO extends AuthLoginVO {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty("openid")
    private String openid;

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

    @ApiModelProperty("创建时间")
    private Date createTime;

}
