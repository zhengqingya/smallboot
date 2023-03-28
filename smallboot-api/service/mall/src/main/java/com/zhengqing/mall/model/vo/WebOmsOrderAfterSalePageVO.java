package com.zhengqing.mall.model.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.StringJoiner;

/**
 * <p>商城-售后列表-展示视图</p>
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
@ApiModel("web-商城-售后列表-展示视图")
public class WebOmsOrderAfterSalePageVO extends WebOmsOrderAfterSaleBaseVO {

    @ApiModelProperty("封面图")
    private String coverImg;

    @ApiModelProperty("商品名称(多个以英文逗号分隔)")
    private String spuNames;

    @ApiModelProperty("商品数量(关联所有商品的总件数)")
    private Integer spuNum;

    @JsonIgnore
    @ApiModelProperty(value = "关联商品信息", hidden = true)
    private List<OmsOrderItemVO> spuList;

    @ApiModelProperty("实付总金额(单位:分)")
    private Integer payPrice;

    @ApiModelProperty("运费(单位:分 0:包邮)")
    private Integer freight;

    @ApiModelProperty("用户电话")
    private String userPhone;

    @Override
    public void handleData() {
        super.handleData();
        this.coverImg = this.spuList.get(0).getCoverImg();
        StringJoiner spuNameSj = new StringJoiner(",");
        this.spuNum = 0;
        this.spuList.forEach(item -> {
            spuNameSj.add(item.getName());
            this.spuNum += item.getNum();
        });
        this.spuNames = spuNameSj.toString();
    }

}
