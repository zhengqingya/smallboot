package com.zhengqing.cms.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> 内容管理-职位申请-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 18:11
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("内容管理-职位申请-保存-提交参数")
public class CmsJobApplySaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @NotNull(message = "职位id不能为空！")
    @ApiModelProperty("职位id")
    private Integer jobId;


    /**
     * {@link com.zhengqing.cms.enums.CmsJobApplyStatusEnum}
     */
    @ApiModelProperty("状态")
    private Integer status;

    @NotBlank(message = "联系人不能为空！")
    @ApiModelProperty("联系人")
    private String contact;

    @NotBlank(message = "联系电话不能为空！")
    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("备注")
    private String remark;


}
