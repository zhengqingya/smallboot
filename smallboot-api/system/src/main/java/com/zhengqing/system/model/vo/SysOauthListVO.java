package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 系统管理 - 用户三方授权表展示视图
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 22:14
 */
@Data
@ApiModel("系统管理 - 用户三方授权表展示视图")
public class SysOauthListVO {

    @ApiModelProperty(value = "主键id")
    private Integer userReOauthId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "三方id")
    private String openId;

    @ApiModelProperty(value = "第三方授权类型")
    private Integer oauthType;

}
