package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import com.zhengqing.mall.model.bo.PmsSkuSpecBO;
import com.zhengqing.mall.model.enums.PmsSpuRateOperatorTypeEnum;
import com.zhengqing.mall.model.enums.PmsSpuRateTypeEnum;
import com.zhengqing.mall.config.mybatis.handler.MallListSpecTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * <p>  商城-商品评价 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 17:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pms_spu_rate", autoResultMap = true)
@ApiModel("商城-商品评价")
public class PmsSpuRate extends BaseEntity<PmsSpuRate> {

    @ApiModelProperty("ID")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("父ID")
    private String parentId;

    @ApiModelProperty("订单关联商品id")
    private String orderItemId;

    @ApiModelProperty("商品id")
    private String spuId;

    @ApiModelProperty("商品sku-id")
    private String skuId;

    @ApiModelProperty("商品规格属性")
    @TableField(typeHandler = MallListSpecTypeHandler.class)
    private List<PmsSkuSpecBO> specList;

    /**
     * {@link PmsSpuRateOperatorTypeEnum}
     */
    @ApiModelProperty("操作人类型(1->买家 2->商家)")
    private Byte operatorType;

    @ApiModelProperty("操作人id")
    private Long operatorId;

    @ApiModelProperty("操作人名称")
    private String operatorName;

    @ApiModelProperty("操作人头像")
    private String operatorIcon;

    @ApiModelProperty("评价图片或视频")
    private String resourceJson;

    @ApiModelProperty("评价内容")
    private String content;

    @ApiModelProperty("是否显示(false->隐藏；true->显示)")
    private Boolean isShow;

    @ApiModelProperty("描述相符")
    private Byte descLevel;

    @ApiModelProperty("物流服务")
    private Byte logisticsLevel;

    @ApiModelProperty("服务态度")
    private Byte serviceLevel;

    @ApiModelProperty("是否含有图片(false->否,true->是)")
    private Boolean isImg;

    @ApiModelProperty("是否含有视频(false->否,true->是)")
    private Boolean isVideo;

    /**
     * {@link PmsSpuRateTypeEnum}
     */
    @ApiModelProperty("评价分类(1->好评,2->差评,3->一般)")
    private Byte rateType;

}
