package com.zhengqing.mall.mini.model.vo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.mall.common.model.bo.MallFileBO;
import com.zhengqing.mall.common.model.bo.PmsSkuSpecBO;
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
 * <p>商城-商品评价-响应参数</p>
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
@ApiModel("mini-商城-商品评价-响应参数")
public class MiniPmsSpuRatePageVO extends BaseVO {

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("操作人id")
    private Long operatorId;

    @ApiModelProperty("操作人名称")
    private String operatorName;

    @ApiModelProperty("评价内容")
    private String content;

    @JsonIgnore
    @ApiModelProperty(value = "评价图片或视频", hidden = true)
    private String resourceJson;

    @ApiModelProperty(value = "评价图片或视频")
    private List<MallFileBO> resourceList;

    @JsonIgnore
    @ApiModelProperty(value = "商品规格属性", hidden = true)
    private String specJson;

    @ApiModelProperty(value = "商品规格")
    private List<PmsSkuSpecBO> specList;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public void handleData() {
        this.resourceList = JSON.parseArray(this.resourceJson, MallFileBO.class);
        this.specList = JSON.parseArray(this.specJson, PmsSkuSpecBO.class);
    }

}
