package com.zhengqing.wxmp.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.wxmp.model.bo.WxMpTemplateMsgDataBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p> 微信公众号-模板消息-发送消息-提交参数 </p>
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
@ApiModel("微信公众号-模板消息-发送消息-提交参数")
public class WxMpTemplateMsgSendDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(message = "主键ID不能为空!")
    private Integer id;

    @NotBlank(message = "AppID不能为空！")
    @ApiModelProperty("AppID")
    private String appId;

    @NotBlank(message = "模板ID不能为空！")
    @ApiModelProperty("模板ID")
    private String templateId;

    @NotEmpty
    @ApiModelProperty("模板数据")
    private List<WxMpTemplateMsgDataBO> dataList;

    @ApiModelProperty("微信openid")
    private String openid;

}
