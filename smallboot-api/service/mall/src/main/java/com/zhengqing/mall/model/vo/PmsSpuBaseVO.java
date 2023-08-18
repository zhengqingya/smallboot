package com.zhengqing.mall.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.common.core.custom.fileprefix.FilePrefix;
import com.zhengqing.mall.model.bo.PmsSkuBO;
import com.zhengqing.mall.model.enums.PmsSpuPresellStatusEnum;
import com.zhengqing.mall.model.enums.PmsSpuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * <p>商城-商品base-展示视图</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-商品base-展示视图")
public class PmsSpuBaseVO extends BaseVO {

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("名称")
    private String name;

    @FilePrefix
    @ApiModelProperty("封面图")
    private String coverImg;

    /**
     * {@link PmsSpuTypeEnum}
     */
    @ApiModelProperty("类型")
    private Byte type;

    @ApiModelProperty("类型值")
    private String typeName;

    @ApiModelProperty("是否上架(false->下架；true->上架)")
    private Boolean isPut;

    @ApiModelProperty("是否显示(false->隐藏；true->显示)")
    private Boolean isShow;

    @ApiModelProperty("是否预售(false->否；true->是)")
    private Boolean isPresell;

    @ApiModelProperty("预售开始时间")
    private Date presellStartTime;

    @ApiModelProperty("预售结束时间")
    private Date presellEndTime;

    @ApiModelProperty("预售-发货日期(购买之后？天之后发货)")
    private Integer presellDeliverDay;

    @ApiModelProperty("商品划线价格(单位:分)")
    private Integer linePrice;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("运费(单位:分 0:包邮)")
    private Integer freight;

    // =========== ↓↓↓↓↓↓ 下面为规格表中计算出的数据值 ↓↓↓↓↓↓ ============

    @ApiModelProperty("商品销售价格(单位:分)")
    private Integer sellPrice;

    @ApiModelProperty("预售价格(单位:分)")
    private Integer presellPrice;

    @ApiModelProperty("每人限购")
    private Integer limitCount;

    @ApiModelProperty("总库存")
    private Integer totalStock;

    @ApiModelProperty("可用库存")
    private Integer usableStock;

    @ApiModelProperty("已用库存")
    private Integer useStock;

    @ApiModelProperty("最小商品价格(单位:分) - 预售/销售时显示对应的最小价")
    private Integer minPrice;

    @ApiModelProperty("最大商品价格(单位:分) - 预售/销售时显示对应的最大价")
    private Integer maxPrice;

    /**
     * {@link PmsSpuPresellStatusEnum }
     */
    @ApiModelProperty("预售状态")
    private Byte presellStatus;

    public void handleData(List<?> skuList) {
        this.handleSkuData(skuList);
        this.typeName = PmsSpuTypeEnum.getEnum(this.type).getDesc();
        // 是否获取预售价格区间值，否则取销售区间价格
        final boolean isPresellValue;
        if (this.isPresell) {
            Date nowTime = new Date();
            // 是否预售中
            boolean isPresellIng = nowTime.after(this.presellStartTime) && nowTime.before(this.presellEndTime);
            if (isPresellIng) {
                // 预售中
                this.presellStatus = PmsSpuPresellStatusEnum.ING.getStatus();
            } else {
                // 根据预售开始时间 取 预售前 | 预售后
                this.presellStatus = nowTime.before(this.presellStartTime) ?
                        PmsSpuPresellStatusEnum.BEFORE.getStatus() : PmsSpuPresellStatusEnum.AFTER.getStatus();
            }
            // 预售价格如果为空则取销售价格
            isPresellValue = this.presellPrice != null && isPresellIng;
            // 是否预售值根据预售时间动态计算
            this.isPresell = nowTime.before(this.presellEndTime);
        } else {
            this.presellStartTime = null;
            this.presellEndTime = null;
            this.presellDeliverDay = null;
            isPresellValue = false;
        }
        this.minPrice = skuList.stream().map(e -> {
            PmsSkuBO pmsSkuBO = (PmsSkuBO) e;
            // 预售价格为0的时候，标识为空，取销售价
            Integer presellPriceItem = pmsSkuBO.getPresellPrice();
            return isPresellValue && presellPriceItem != 0 ? presellPriceItem : pmsSkuBO.getSellPrice();
        }).min(Integer::compareTo).get();
        this.maxPrice = skuList.stream().map(e -> {
            PmsSkuBO pmsSkuBO = (PmsSkuBO) e;
            // 预售价格为0的时候，标识为空，取销售价
            Integer presellPriceItem = pmsSkuBO.getPresellPrice();
            return isPresellValue && presellPriceItem != 0 ? presellPriceItem : pmsSkuBO.getSellPrice();
        }).max(Integer::compareTo).get();
    }

    private void handleSkuData(List<?> skuList) {
        this.totalStock = skuList.stream().mapToInt(e -> {
            PmsSkuBO pmsSkuBO = (PmsSkuBO) e;
            return pmsSkuBO.getTotalStock();
        }).sum();
        this.useStock = skuList.stream().mapToInt(e -> {
            PmsSkuBO pmsSkuBO = (PmsSkuBO) e;
            return pmsSkuBO.getUseStock();
        }).sum();
        this.usableStock = skuList.stream().mapToInt(e -> {
            PmsSkuBO pmsSkuBO = (PmsSkuBO) e;
            return pmsSkuBO.getUsableStock();
        }).sum();
        this.sellPrice = skuList.stream().mapToInt(e -> {
            PmsSkuBO pmsSkuBO = (PmsSkuBO) e;
            return pmsSkuBO.getSellPrice();
        }).sum();
        this.presellPrice = skuList.stream().mapToInt(e -> {
            PmsSkuBO pmsSkuBO = (PmsSkuBO) e;
            return pmsSkuBO.getPresellPrice();
        }).sum();
        this.limitCount = skuList.stream().mapToInt(e -> {
            PmsSkuBO pmsSkuBO = (PmsSkuBO) e;
            return pmsSkuBO.getLimitCount();
        }).sum();
    }

}
