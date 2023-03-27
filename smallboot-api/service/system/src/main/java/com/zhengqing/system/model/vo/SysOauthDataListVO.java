package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 第三方授权数据展示参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/12/6 13:50
 */
@Data
@ApiModel("第三方授权数据展示参数")
public class SysOauthDataListVO {

    @ApiModelProperty(value = "三方绑定主键id（做解除绑定时使用）")
    private Integer userReOauthId;

    @ApiModelProperty(value = "第三方授权类型")
    private Integer oauthType;

    @ApiModelProperty(value = "第三方授权类型名称")
    private String oauthTypeName;

    @ApiModelProperty(value = "第三方授权类型绑定名称")
    private String oauthTypeBindName;

    @ApiModelProperty(value = "第三方授权类型描述")
    private String oauthTypeDesc;

    @ApiModelProperty(value = "第三方授权类型名称")
    private Integer ifBind;

}
