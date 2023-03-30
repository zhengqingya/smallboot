package com.zhengqing.mall.model.dto;

import cn.hutool.core.lang.Assert;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.parameter.CheckParam;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.mall.model.bo.MallDictBO;
import com.zhengqing.mall.model.bo.MallFileBO;
import com.zhengqing.mall.model.bo.WebPmsAttrBO;
import com.zhengqing.mall.model.enums.PmsSpuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * <p> 商城-商品-保存参数 </p>
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
@ApiModel("web-商城-商品-保存参数")
public class WebPmsSpuSaveDTO extends BaseDTO implements CheckParam {

    @NotNull(message = "商品ID不能为空", groups = {UpdateGroup.class})
    @ApiModelProperty(value = "商品ID", example = "1")
    private String id;

    /**
     * {@link PmsSpuTypeEnum}
     */
    @NotNull(message = "商品类型不能为空！")
    @ApiModelProperty(value = "商品类型", required = true, example = "101")
    private Byte type;

    @Length(max = 30, message = "商品名称最多30字！")
    @NotBlank(message = "商品名称不能为空！")
    @ApiModelProperty(value = "商品名称", required = true, example = "熊猫限定帆布袋")
    private String name;

    @NotBlank(message = "商品封面不能为空！")
    @ApiModelProperty(value = "封面图", required = true, example = "https://www.zhengqingya.com/test.png")
    private String coverImg;

    @Valid
    @Size(min = 1, max = 5, message = "轮播图范围1至5张")
    @ApiModelProperty(value = "轮播图", required = true, example = "[{\"name\": \"熊猫限定帆布袋\",\"url\": \"https://www.zhengqingya.com/test.png\"}]")
    private List<MallFileBO> slideImgList;

    @Valid
    @Size(min = 0, max = 20, message = "商品详情图最多20张")
    @ApiModelProperty(value = "商品详情图", required = true, example = "[{\"name\": \"熊猫限定帆布袋\",\"url\": \"https://www.zhengqingya.com/test.png\"}]")
    private List<MallFileBO> detailImgList;

    // ============================ ↓↓↓↓↓↓ 规格相关处理 start ↓↓↓↓↓↓ ============================

    @Valid
    @NotEmpty(message = "商品属性丢失！")
    @ApiModelProperty("商品属性")
    private List<WebPmsAttrBO> attrList;

    @Valid
    @NotEmpty(message = "规格数据丢失！")
    @ApiModelProperty("规格明细列表")
    private List<WebPmsSkuSaveDTO> skuList;

    // ============================ ↑↑↑↑↑↑ 规格相关处理 end ↑↑↑↑↑↑ ============================

    @ApiModelProperty(value = "商品划线价格(单位:分)", example = "100")
    private Integer linePrice;

    @NotNull(message = "排序不能为空！")
    @ApiModelProperty(value = "排序", example = "1", required = true)
    private Integer sort;

    @NotNull(message = "运费不能为空！")
    @ApiModelProperty(value = "运费(单位:分 0:包邮)", example = "0")
    private Integer freight;

    @ApiModelProperty(value = "优惠券ID", example = "1")
    private Long couponId;

    @ApiModelProperty(value = "优惠券名称", example = "买一送一")
    private String couponName;

    @ApiModelProperty(value = "优惠券数量", example = "100")
    private Integer couponNum;

    @NotNull(message = "是否预售不能为空！")
    @ApiModelProperty(value = "是否预售", required = true, example = "true")
    private Boolean isPresell;

    @ApiModelProperty(value = "预售开始时间", example = "2021-08-25 09:00:00")
    private Date presellStartTime;

    @ApiModelProperty(value = "预售结束时间", example = "2021-08-26 23:59:59")
    private Date presellEndTime;

    @ApiModelProperty(value = "预售-发货日期(购买之后？天之后发货)", example = "15")
    private Integer presellDeliverDay;

    @NotNull(message = "是否上架不能为空！")
    @ApiModelProperty(value = "是否上架", required = true, example = "true")
    private Boolean isPut;

    @NotNull(message = "是否显示不能为空！")
    @ApiModelProperty(value = "是否显示", required = true, example = "true")
    private Boolean isShow;

    @Valid
    @ApiModelProperty(value = "商品关联服务")
    private List<MallDictBO> serviceList;

    @Valid
    @ApiModelProperty(value = "商品关联说明")
    private List<MallDictBO> explainList;

    @Override
    public void checkParam() {
        // 校验优惠券
        if (PmsSpuTypeEnum.VIRTUAL_COUPON.getType().equals(this.type)) {
            Assert.notNull(this.couponId, "优惠券id不能为空！");
            Assert.notBlank(this.couponName, "优惠券名称不能为空！");
            Assert.notNull(this.couponNum, "优惠券数量不能为空！");
        }


        // 校验预售
        if (this.isPresell) {
            Assert.isFalse(this.presellStartTime == null || this.presellEndTime == null, "预售时间不能为空！");
        } else {
            this.presellStartTime = null;
            this.presellEndTime = null;
            this.presellDeliverDay = null;
        }

        // 最终再处理一次属性值
        this.skuList.forEach(item -> {
            if (this.linePrice != null) {
                Assert.isTrue(this.linePrice >= item.getSellPrice(), "划线价格不能低于销售价格！");
            }
            if (item.getLimitCount() == null) {
                item.setLimitCount(0);
            }
            Assert.isTrue(item.getLimitCount() <= item.getTotalStock(), "限购数量不能大于总库存！");
            if (item.getPresellPrice() == null) {
                item.setPresellPrice(0);
            }
        });
    }

}
