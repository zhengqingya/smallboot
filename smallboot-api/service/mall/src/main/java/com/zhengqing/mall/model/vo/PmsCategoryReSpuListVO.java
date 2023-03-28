package com.zhengqing.mall.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.mall.model.bo.MiniPmsCategoryReSkuBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>商城-分类关联商品-展示视图</p>
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
@ApiModel("商城-分类关联商品-列表-展示视图")
public class PmsCategoryReSpuListVO extends BaseVO {

    @JsonIgnore
    @ApiModelProperty(value = "分类ID", hidden = true)
    private String categoryId;

    @JsonIgnore
    @ApiModelProperty(value = "分类关联商品是否上架(false->下架；true->上架)", hidden = true)
    private Boolean categoryReSpuIsPut;

    @ApiModelProperty("商品ID")
    private String id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品封面图")
    private String coverImg;

    @ApiModelProperty(value = "商品规格", required = true)
    private List<MiniPmsCategoryReSkuBO> skuList;

    @ApiModelProperty("商品sku总可用库存")
    private Integer usableStockSum;

    @ApiModelProperty("商品是否上架(false->下架；true->上架)")
    private Boolean isPut;

    public void handleData() {
        if (CollectionUtils.isEmpty(this.skuList)) {
            this.skuList = Lists.newLinkedList();
        }
        this.usableStockSum = this.skuList.stream().mapToInt(MiniPmsCategoryReSkuBO::getUsableStock).sum();
        if (this.isPut) {
            // 商品上架后，再去看分类是否上架
            this.isPut = this.categoryReSpuIsPut;
        }
    }

}
