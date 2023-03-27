package com.zhengqing.wxmp.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>微信公众号-模板消息-模板数据-响应参数</p>
 *
 * @author zhengqingya
 * @description {@link me.chanjar.weixin.mp.bean.template.WxMpTemplateData}
 * @date 2023/03/15 18:29
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WxMpTemplateMsgDataBO extends BaseBO {

    @ApiModelProperty("键")
    private String name;

    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("颜色")
    private String color;

}