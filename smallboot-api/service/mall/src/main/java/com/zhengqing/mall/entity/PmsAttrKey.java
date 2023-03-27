package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  商城-属性key </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/20 17:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("pms_attr_key")
@ApiModel("商城-属性key")
public class PmsAttrKey extends BaseEntity<PmsAttrKey> {

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("属性key名称")
    private String attrKeyName;

    @ApiModelProperty("排序")
    private Integer sort;

}
