package com.zhengqing.mall.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.StringJoiner;

/**
 * <p>商城-订单信息-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-订单信息-响应参数")
public class WebOmsOrderPageVO extends WebOmsOrderBaseVO {

    @ApiModelProperty("商品名称(多个以英文逗号分隔)")
    private String spuNames;

    @ApiModelProperty("商品数量(关联所有商品的总件数)")
    private Integer spuNum;

    @ApiModelProperty("封面图")
    private String coverImg;

    @Override
    public void handleData() {
        super.handleData();
        this.coverImg = super.getSpuList().get(0).getCoverImg();
        StringJoiner spuNameSj = new StringJoiner(",");
        this.spuNum = 0;
        super.getSpuList().forEach(item -> {
            spuNameSj.add(item.getName());
            this.spuNum += item.getNum();
        });
        this.spuNames = spuNameSj.toString();
    }

}
