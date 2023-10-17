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
 * <p> 内容管理-职位申请-分页列表-请求参数 </p>
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
@ApiModel("内容管理-职位申请-分页列表-请求参数")
public class CmsJobApplyPageDTO extends BaseDTO {

    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;

    @ApiModelProperty(value = "部门ID")
    private Integer deptId;

    @ApiModelProperty("创建人")
    private Long createBy;

    @ApiModelProperty("职位id")
    private Integer jobId;

    /**
     * {@link com.zhengqing.cms.enums.CmsJobApplyStatusEnum}
     */
    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("联系人")
    private String contact;

    @ApiModelProperty("联系电话")
    private String contactPhone;


}
