package com.zhengqing.wf.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.wf.model.bo.WfFormConfigBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p>工作流-流程部署-分页列表-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("工作流-流程部署-分页列表-响应参数")
public class WfDeployPageVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("配置")
    private WfFormConfigBO config;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
