package com.zhengqing.mall.model.dto;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.parameter.CheckParam;
import com.zhengqing.mall.model.bo.MallFileBO;
import com.zhengqing.mall.model.bo.PmsSkuSpecBO;
import com.zhengqing.mall.model.enums.PmsSpuRateOperatorTypeEnum;
import com.zhengqing.mall.model.enums.PmsSpuRateTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p> 商城-商品评价-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 17:10
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商城-商品评价-保存-提交参数")
public class MiniPmsSpuRateSaveDTO extends BaseDTO implements CheckParam {

    /**
     * {@link PmsSpuRateOperatorTypeEnum}
     */
    @NotNull(message = "操作人类型不能为空")
    @ApiModelProperty(value = "操作人类型(1->买家 2->商家)", required = true, example = "1")
    private Byte operatorType;

    @NotNull(message = "操作人id不能为空")
    @ApiModelProperty(value = "操作人id", required = true, example = "1")
    private Long operatorId;

    @NotBlank(message = "操作人名称不能为空")
    @ApiModelProperty(value = "操作人名称", required = true, example = "米西米西")
    private String operatorName;

    @NotBlank(message = "操作人头像不能为空")
    @ApiModelProperty(value = "操作人头像", required = true, example = "https://www.zhengqingya.com/test.png")
    private String operatorIcon;

    @Valid
    @NotEmpty(message = "商品评价信息不能为空")
    @ApiModelProperty(value = "商品评价信息", required = true)
    private List<RateInfo> rateList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("mini-商城-商品评价")
    public static class RateInfo {

        @NotNull(message = "订单关联商品id不能为空")
        @ApiModelProperty(value = "订单关联商品id", required = true, example = "1")
        private String orderItemId;

        @NotNull(message = "商品id不能为空")
        @ApiModelProperty(value = "商品id", required = true, example = "1")
        private String spuId;

        @NotBlank(message = "商品sku-id不能为空")
        @ApiModelProperty(value = "商品sku-id", required = true, example = "1")
        private String skuId;

        @NotEmpty(message = "商品规格不能为空！")
        @ApiModelProperty(value = "商品规格", required = true)
        private List<PmsSkuSpecBO> specList;

        @Valid
        @ApiModelProperty(value = "评价图片或视频")
        private List<MallFileBO> resourceList;

        @JsonIgnore
        @ApiModelProperty(value = "评价图片或视频", hidden = true)
        private String resourceJson;

        @ApiModelProperty(value = "评价内容", example = "nice nice nice.")
        private String content;

        @Range(min = 1, max = 5, message = "评价星星范围值：1-5")
        @NotNull(message = "描述相符不能为空")
        @ApiModelProperty(value = "描述相符", required = true, example = "1")
        private Byte descLevel;

        @Range(min = 1, max = 5, message = "评价星星范围值：1-5")
        @NotNull(message = "物流服务不能为空")
        @ApiModelProperty(value = "物流服务", required = true, example = "1")
        private Byte logisticsLevel;

        @Range(min = 1, max = 5, message = "评价星星范围值：1-5")
        @NotNull(message = "服务态度不能为空")
        @ApiModelProperty(value = "服务态度", required = true, example = "1")
        private Byte serviceLevel;

        @NotNull(message = "是否含有图片不能为空")
        @ApiModelProperty(value = "是否含有图片(false->否,true->是)", required = true, example = "true")
        private Boolean isImg;

        @NotNull(message = "是否含有视频不能为空")
        @ApiModelProperty(value = "是否含有视频(false->否,true->是)", required = true, example = "true")
        private Boolean isVideo;

        /**
         * {@link PmsSpuRateTypeEnum}
         */
        @JsonIgnore
        @ApiModelProperty(value = "评价分类(1->好评,2->差评,3->一般)", hidden = true)
        private Byte rateType;
    }


    @Override
    public void checkParam() {
        this.rateList.forEach(item -> {
            item.resourceJson = JSON.toJSONString(item.resourceList);
            Integer sumLevel = item.descLevel + item.logisticsLevel + item.serviceLevel;
            if (sumLevel >= 9) {
                item.rateType = PmsSpuRateTypeEnum.GOOD.getType();
            } else if (sumLevel > 3 && sumLevel < 9) {
                item.rateType = PmsSpuRateTypeEnum.BAD.getType();
            } else if (sumLevel == 3) {
                item.rateType = PmsSpuRateTypeEnum.GENERAL.getType();
            }
        });
    }

}
