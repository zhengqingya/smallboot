package com.zhengqing.mall.common.model.vo;

import com.zhengqing.mall.common.model.bo.MallDictBO;
import com.zhengqing.mall.common.model.bo.MallFileBO;
import com.zhengqing.mall.web.model.bo.WebPmsAttrBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>商城-商品-详情-展示视图</p>
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
@ApiModel("商城-商品-详情-展示视图")
public class PmsSpuDetailVO extends PmsSpuBaseVO {

    @ApiModelProperty("商品属性")
    private List<WebPmsAttrBO> attrList;

    @ApiModelProperty(value = "轮播图")
    private List<MallFileBO> slideImgList;

    @ApiModelProperty("商品详情图")
    private List<MallFileBO> detailImgList;

    @ApiModelProperty(value = "商品关联服务")
    private List<MallDictBO> serviceList;

    @ApiModelProperty(value = "商品关联说明")
    private List<MallDictBO> explainList;

    @Override
    public void handleData(List<?> skuList) {
        super.handleData(skuList);
    }

}
