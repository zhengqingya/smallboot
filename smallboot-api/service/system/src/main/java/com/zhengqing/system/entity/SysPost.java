package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  系统管理-岗位 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 17:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_post")
@ApiModel("系统管理-岗位")
public class SysPost extends BaseEntity<SysPost> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    /**
     * 值为空时，MP更新数据库时不忽略此字段值
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @ApiModelProperty("岗位名称")
    private String name;

    @ApiModelProperty("岗位编码")
    private String code;

    @ApiModelProperty("状态(1:正常 0:停用)")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("备注")
    private String remark;

}
