package com.zhengqing.wxmp.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 微信公众号-账号-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("微信公众号-账号-分页列表-请求参数")
public class WxAccountPageDTO extends BaseDTO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("公众号ID")
    private String account;

    @ApiModelProperty("AppID")
    private String appId;

}
