package com.zhengqing.cms.model.vo;

import com.zhengqing.cms.enums.CmsJobApplyStatusEnum;
import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p>内容管理-职位申请-分页列表-响应参数</p>
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
@ApiModel("内容管理-职位申请-分页列表-响应参数")
public class CmsJobApplyPageVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("职位id")
    private Integer jobId;

    @ApiModelProperty("职位名称")
    private String jobName;


    /**
     * {@link com.zhengqing.cms.enums.CmsJobApplyStatusEnum}
     */
    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("状态描述")
    private String statusName;

    @ApiModelProperty("联系人")
    private String contact;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;


    public void handleData() {
        this.statusName = CmsJobApplyStatusEnum.getEnum(this.status).getDesc();
    }

}
