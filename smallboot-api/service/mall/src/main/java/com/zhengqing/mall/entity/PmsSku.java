package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zhengqing.mall.config.mybatis.handler.MallListSpecTypeHandler;
import com.zhengqing.mall.model.bo.PmsSkuSpecBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * <p>  商城-商品规格 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pms_sku", autoResultMap = true)
@ApiModel("商城-商品规格")
public class PmsSku extends Model<PmsSku> {

    @ApiModelProperty("ID")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("商品ID")
    private String spuId;

    @ApiModelProperty("规格编码")
    private String code;

    @ApiModelProperty("商品规格-属性")
    @TableField(typeHandler = MallListSpecTypeHandler.class)
    private List<PmsSkuSpecBO> specList;

    @ApiModelProperty("成本价(单位:分)")
    private Integer costPrice;

    @ApiModelProperty("预售价格(单位:分  为0时标识)")
    private Integer presellPrice;

    @ApiModelProperty("销售单价(单位:分)")
    private Integer sellPrice;

    @ApiModelProperty("每人限购")
    private Integer limitCount;

    @ApiModelProperty("总库存")
    private Integer totalStock;

    @ApiModelProperty("可用库存")
    private Integer usableStock;

    @ApiModelProperty("已用库存")
    private Integer useStock;

    @ApiModelProperty("虚拟-已用库存(虚拟销量)")
    private Integer virtualUseStock;

    @ApiModelProperty("封面图")
    private String coverImg;

    @ApiModelProperty("是否显示(false->否 true->是)")
    private Boolean isShow;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //    @TableLogic
//    @ApiModelProperty(value = "是否删除：true->删除，false->未删除")
//    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
//    private Boolean isDeleted;

}
