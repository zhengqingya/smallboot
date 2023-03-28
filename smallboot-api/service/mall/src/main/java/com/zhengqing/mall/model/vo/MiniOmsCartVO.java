package com.zhengqing.mall.model.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Joiner;
import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.mall.model.bo.PmsSkuSpecBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> mini-商城-购物车-响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/12 17:27
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class MiniOmsCartVO extends BaseVO {

    @ApiModelProperty(value = "用户id", example = "1")
    private Long userId;

    @ApiModelProperty(value = "商品id", example = "1")
    private String spuId;

    @ApiModelProperty(value = "商品规格ID(redis购物车中的hashKey-标识一个sku的商品)", example = "888")
    private String skuId;

    @ApiModelProperty(value = "商品数量", example = "1")
    private Integer num;

    @JsonIgnore
    @ApiModelProperty(value = "操作时间", hidden = true)
    private Date time;


    // ====================== 上：redis中获取数据  ======================

    @ApiModelProperty("是否失效(true->是 false->否)")
    private Boolean isLose;

    // ====================== 下：
    //                           小程序端提交时存入redis中作为商品失效时的数据快照 -- 暂时不要快照
    //                           返回给小程序端时从mysql中查询实时同步数据
    // ======================

    @ApiModelProperty(value = "商品规格sku属性(前端用于购物车列表展示使用)")
    private List<PmsSkuSpecBO> specList;

    @ApiModelProperty(value = "商品规格sku属性(前端用于购物车列表展示使用)")
    private String specDesc;

    @ApiModelProperty(value = "商品名称", example = "小熊猫")
    private String name;

    @ApiModelProperty(value = "封面图", example = "https://www.zhengqingya.com/test.png")
    private String coverImg;

    @ApiModelProperty(value = "商品价格(单位:分)", example = "100")
    private Integer price;

    @ApiModelProperty("可用库存")
    private Integer usableStock;

    @ApiModelProperty("每人限购")
    private Integer limitCount;

    @ApiModelProperty("运费(单位:分 0:包邮)")
    private Integer freight;

    public void handleData() {
        List<String> specAttrList = this.specList.stream().map(e -> e.getAttrValueName()).collect(Collectors.toList());
        this.specDesc = Joiner.on(",").join(specAttrList);
    }

}
