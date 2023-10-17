package com.zhengqing.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  内容管理-职位申请 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 18:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cms_job_apply")
@ApiModel("内容管理-职位申请")
public class CmsJobApply extends IsDeletedBaseEntity<CmsJobApply> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("部门id")
    private Integer deptId;
    
    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;

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

    @ApiModelProperty("备注")
    private String remark;

}
