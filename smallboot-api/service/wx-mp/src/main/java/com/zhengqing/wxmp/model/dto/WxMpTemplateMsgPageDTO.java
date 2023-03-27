package com.zhengqing.wxmp.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 微信公众号-模板消息-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:29
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("微信公众号-模板消息-分页列表-请求参数")
public class WxMpTemplateMsgPageDTO extends BaseDTO {

    @JsonIgnore
    @ApiModelProperty(value = "AppID", hidden = true)
    private String appId;

    @ApiModelProperty("模板标题")
    private String title;

}
