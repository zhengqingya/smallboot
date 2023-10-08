package com.zhengqing.wxmp.entity;

import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.collect.Lists;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import com.zhengqing.wxmp.config.mybatis.handler.WxMpTemplateMsgDataTypeHandler;
import com.zhengqing.wxmp.model.bo.WxMpTemplateMsgDataBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * <p>  微信公众号-模板消息 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_wx_mp_template_msg", autoResultMap = true)
@ApiModel("微信公众号-模板消息")
public class WxMpTemplateMsg extends IsDeletedBaseEntity<WxMpTemplateMsg> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;
    
    @ApiModelProperty("AppID")
    private String appId;

    @ApiModelProperty("模板ID")
    private String templateId;

    @ApiModelProperty("模板标题")
    private String title;

    @ApiModelProperty("模板内容")
    private String content;

    @ApiModelProperty("模板数据")
    @TableField(typeHandler = WxMpTemplateMsgDataTypeHandler.class)
    private List<WxMpTemplateMsgDataBO> dataList;

    public WxMpTemplateMsg handleData() {
        if (super.getIsDeleted()) {
            // 如果模板不存在才构建新数据
            this.dataList = Lists.newArrayList();
            List<String> list = ReUtil.findAll("\\{\\{(\\w*)\\.DATA\\}\\}", this.content, 1);
            list.forEach(item -> this.dataList.add(WxMpTemplateMsgDataBO.builder()
                    .name(item)
                    .value("")
                    .color("#000")
                    .build()));
        }
        return this;
    }

}
