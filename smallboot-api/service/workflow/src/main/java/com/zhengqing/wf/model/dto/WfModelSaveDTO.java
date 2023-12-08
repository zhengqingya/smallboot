package com.zhengqing.wf.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.wf.model.vo.WfModelMetaInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * <p> 工作流-模型-保存-提交参数 </p>
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
@ApiModel("工作流-模型-保存-提交参数")
public class WfModelSaveDTO extends BaseDTO {

    @ApiModelProperty("模型ID")
    @NotBlank(groups = {UpdateGroup.class}, message = "模型ID不能为空!")
    private String id;

    @NotBlank(message = "模型名称不能为空！")
    @ApiModelProperty("模型名称")
    private String name;

    @ApiModelProperty("模型Key")
    private String key;

    @ApiModelProperty("分类编码")
    private String category;

    @ApiModelProperty("元数据")
    private WfModelMetaInfoVO metaInfoObj;

    @ApiModelProperty("流程xml")
    private String bpmnXml;

    @ApiModelProperty("是否保存为新版本")
    private Boolean isNewVersion;

}
