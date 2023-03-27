package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 用户个人中心
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:18
 */
@Data
@ApiModel("用户个人中心")
public class SysUserInfoVO {

    @ApiModelProperty(value = "用户账号")
    private String username;

    @ApiModelProperty(value = "用户名称")
    private String nickname;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "性别")
    private String sexName;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

}
