package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import com.zhengqing.system.enums.SysAppStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  系统管理-版本记录 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/15 14:58
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_version")
@ApiModel("系统管理-版本记录")
public class SysVersion extends IsDeletedBaseEntity<SysVersion> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("版本号")
    private String version;

    @ApiModelProperty("名称")
    private String name;

    /**
     * {@link SysAppStatusEnum}
     */
    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("小程序审核结果")
    private String appAuditResultList;

    @ApiModelProperty("备注")
    private String remark;


    /**
     * {@link com.zhengqing.system.enums.SysVersionTypeEnum}
     */
    @ApiModelProperty("类型")
    private Integer type;

}
