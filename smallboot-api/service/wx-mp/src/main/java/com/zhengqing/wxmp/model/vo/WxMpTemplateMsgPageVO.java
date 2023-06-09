package com.zhengqing.wxmp.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.wxmp.model.bo.WxMpTemplateMsgDataBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>微信公众号-模板消息-分页列表-响应参数</p>
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
@ApiModel("微信公众号-模板消息-分页列表-响应参数")
public class WxMpTemplateMsgPageVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("AppID")
    private String appId;

    @ApiModelProperty("模板ID")
    private String templateId;

    @ApiModelProperty("模板标题")
    private String title;

    @ApiModelProperty("模板内容")
    private String content;

    @ApiModelProperty("模板数据")
    private List<WxMpTemplateMsgDataBO> dataList;

    public void handleData() {

    }

}
