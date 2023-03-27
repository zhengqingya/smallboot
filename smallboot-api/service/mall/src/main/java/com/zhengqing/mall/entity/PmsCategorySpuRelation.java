package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  商城-分类关联商品 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 16:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("pms_category_spu_relation")
@ApiModel("商城-分类关联商品")
public class PmsCategorySpuRelation extends BaseEntity<PmsCategorySpuRelation> {

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("分类id")
    private String categoryId;

    @ApiModelProperty("商品id")
    private String spuId;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("是否显示(false->否,true->是)")
    private Boolean isShow;

    @ApiModelProperty("是否上架(false->下架,true->上架)")
    private Boolean isPut;

}
