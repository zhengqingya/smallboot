package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zhengqing.common.db.entity.BaseEntity;
import com.zhengqing.mall.config.mybatis.handler.MallListAttrTypeHandler;
import com.zhengqing.mall.config.mybatis.handler.MallListDictTypeHandler;
import com.zhengqing.mall.config.mybatis.handler.MallListFileTypeHandler;
import com.zhengqing.mall.model.bo.MallDictBO;
import com.zhengqing.mall.model.bo.MallFileBO;
import com.zhengqing.mall.model.bo.WebPmsAttrBO;
import com.zhengqing.mall.model.enums.PmsSpuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * <p> 商城-商品 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pms_spu", autoResultMap = true)
@ApiModel("商城-商品")
public class PmsSpu extends BaseEntity<PmsSpu> {

    @ApiModelProperty("ID")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * {@link PmsSpuTypeEnum}
     */
    @ApiModelProperty("类型")
    private Byte type;

    @ApiModelProperty("优惠券ID")
    private Long couponId;

    @ApiModelProperty("优惠券名称")
    private String couponName;

    @ApiModelProperty("优惠券数量")
    private Integer couponNum;

    @ApiModelProperty("封面图")
    private String coverImg;

    @ApiModelProperty("轮播图")
    @TableField(typeHandler = MallListFileTypeHandler.class)
    private List<MallFileBO> slideImgList;

    @ApiModelProperty("商品详情图")
    @TableField(typeHandler = MallListFileTypeHandler.class)
    private List<MallFileBO> detailImgList;

    /**
     * linePrice值为空时，MP更新数据库时不忽略此字段值
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("商品划线价格(单位:分)")
    private Integer linePrice;

    @ApiModelProperty("运费(单位:分 0:包邮)")
    private Integer freight;

    @ApiModelProperty("商品属性")
    @TableField(typeHandler = MallListAttrTypeHandler.class)
    private List<WebPmsAttrBO> attrList;

    @ApiModelProperty("是否上架(false->下架；true->上架)")
    private Boolean isPut;

    @ApiModelProperty("是否显示(false->隐藏；true->显示)")
    private Boolean isShow;

    @ApiModelProperty("是否预售(0->否；1->是)")
    private Boolean isPresell;

    @ApiModelProperty("预售结束时间")
    private Date presellEndTime;

    @ApiModelProperty("预售开始时间")
    private Date presellStartTime;

    @ApiModelProperty("预售-发货日期(购买之后？天之后发货)")
    private Integer presellDeliverDay;

    @TableField(typeHandler = MallListDictTypeHandler.class)
    @ApiModelProperty(value = "商品关联服务")
    private List<MallDictBO> serviceList;

    @TableField(typeHandler = MallListDictTypeHandler.class)
    @ApiModelProperty(value = "商品关联说明")
    private List<MallDictBO> explainList;

    @ApiModelProperty("商品详情-文案")
    private String description;

}
