package com.zhengqing.wf.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>工作流-流程模型-元数据</p>
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
@ApiModel("工作流-流程模型-元数据")
public class WfModelMetaInfoVO extends BaseVO {

    @ApiModelProperty("表单编号")
    private Long formId;

    @ApiModelProperty("表单类型")
    private Integer formType;

    @ApiModelProperty("流程描述")
    private String description;

    @ApiModelProperty("创建者（username）")
    private String createUser;

}
