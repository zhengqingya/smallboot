package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import com.zhengqing.system.enums.SysConfigTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p> 系统管理-系统配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_config")
@ApiModel("系统管理-系统配置")
public class SysConfig extends BaseEntity<SysConfig> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @TableField(value = "`key`")
    @ApiModelProperty("属性key")
    private String key;

    @ApiModelProperty("属性value")
    private String value;

    @ApiModelProperty("备注")
    private String remark;

    /**
     * {@link SysConfigTypeEnum}
     */
    @ApiModelProperty("类型（1:配置 2:属性）")
    private Integer type;

}
