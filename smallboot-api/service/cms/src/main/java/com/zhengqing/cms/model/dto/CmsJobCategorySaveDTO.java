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
 * <p> 内容管理-招聘岗位分类-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:09
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("内容管理-招聘岗位分类-保存-提交参数")
public class CmsJobCategorySaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @NotNull(message = "请选择商户！")
    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;

    @ApiModelProperty("部门id")
    private Integer deptId;

    @NotBlank(message = "名称不能为空！")
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("状态(0:停用 1:正常)")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;


}
