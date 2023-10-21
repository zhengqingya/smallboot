package com.zhengqing.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  内容管理-招聘岗位标签 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cms_job_tag")
@ApiModel("内容管理-招聘岗位标签")
public class CmsJobTag extends IsDeletedBaseEntity<CmsJobTag> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("部门id")
    private Integer deptId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("状态(0:停用 1:正常)")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

}
