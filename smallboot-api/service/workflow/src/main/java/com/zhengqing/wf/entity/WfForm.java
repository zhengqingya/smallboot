package com.zhengqing.wf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import com.zhengqing.wf.model.bo.WfFormConfigBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  工作流-表单 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("wf_form")
@ApiModel("工作流-表单")
public class WfForm extends IsDeletedBaseEntity<WfForm> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("配置")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private WfFormConfigBO config;

    @ApiModelProperty("备注")
    private String remark;

}
