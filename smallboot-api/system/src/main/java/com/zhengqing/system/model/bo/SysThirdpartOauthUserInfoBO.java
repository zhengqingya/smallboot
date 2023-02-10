package com.zhengqing.system.model.bo;

import com.zhengqing.common.core.enums.UserSexEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 第三方授权用户信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 22:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("第三方授权用户信息")
public class SysThirdpartOauthUserInfoBO {

    @ApiModelProperty(value = "第三方授权类型")
    private Integer oauthType;

    @ApiModelProperty(value = "三方id")
    private String openId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * {@link UserSexEnum}
     */
    @ApiModelProperty(value = "性别")
    private Byte sex;

    @ApiModelProperty(value = "备注")
    private String remark;

}
