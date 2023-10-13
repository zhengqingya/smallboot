package com.zhengqing.cms.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 内容管理-招聘岗位分类-分页列表-请求参数 </p>
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
@ApiModel("内容管理-招聘岗位分类-分页列表-请求参数")
public class CmsJobCategoryPageDTO extends BaseDTO {

    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;
    
    @ApiModelProperty("部门id")
    private Integer deptId;

    @ApiModelProperty("名称")
    private String name;

}
