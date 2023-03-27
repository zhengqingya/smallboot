package com.zhengqing.system.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * qq用户信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/12/2 17:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("qq用户信息")
public class SysQQUserInfoBO {

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "备注")
    private String remark;

}
