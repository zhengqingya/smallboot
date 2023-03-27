package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  商城-属性value </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/20 17:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("pms_attr_value")
@ApiModel("商城-属性value")
public class PmsAttrValue extends BaseEntity<PmsAttrValue> {

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("属性key")
    private String attrKeyId;

    @ApiModelProperty("属性value值")
    private String attrValueName;

    @ApiModelProperty("排序")
    private Integer sort;

}
