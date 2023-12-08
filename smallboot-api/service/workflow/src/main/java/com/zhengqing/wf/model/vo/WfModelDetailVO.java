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
 * <p>工作流-流程模型-详情-响应参数</p>
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
@ApiModel("工作流-流程模型-详情-响应参数")
public class WfModelDetailVO extends BaseVO {

    @ApiModelProperty("模型ID")
    private String id;

    @ApiModelProperty("模型名称")
    private String name;

    @ApiModelProperty("模型Key")
    private String key;

    @ApiModelProperty("分类编码")
    private String category;

    @ApiModelProperty("版本")
    private Integer version;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("元数据")
    private WfModelMetaInfoVO metaInfoObj;

    @ApiModelProperty("流程xml")
    private String bpmnXml;

    @ApiModelProperty("表单配置")
    private WfFormConfigBO formConfigObj;


    private void handleData() {

    }

}
