package com.zhengqing.mall.common.model.vo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p> 商城-订单物流查询-响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 10:46
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-订单物流查询-响应参数")
public class OmsLogisticVO extends BaseVO {

    @ApiModelProperty("物流公司")
    private String logisticsCompany;

    /**
     * {@link com.zhengqing.mall.common.model.enums.TpsLogisticsCodeEnum }
     */
    @ApiModelProperty("快递公司编码")
    private String logisticsCode;

    @ApiModelProperty("快递单号")
    private String logisticsNo;

    @JsonIgnore
    @ApiModelProperty(value = "事件轨迹集", hidden = true)
    private String traceJson;

    @ApiModelProperty("事件轨迹集")
    private List<TraceItem> traceList;

    @ApiModelProperty("送货人名称")
    private String delivererName;

    @ApiModelProperty("送货人电话号码")
    private String delivererPhone;

    @ApiModelProperty("所在城市")
    private String location;

    /**
     * {@link com.zhengqing.mall.common.model.enums.TpsLogisticsStatusEnum }
     */
    @ApiModelProperty("物流状态")
    private Integer status;

    @ApiModelProperty("状态名称")
    private String statusName;

    /**
     * {@link com.zhengqing.mall.common.model.enums.TpsLogisticsStatusEnum }
     */
    @ApiModelProperty("增值物流状态")
    private Integer statusEx;

    @ApiModelProperty("增值物流状态名称")
    private String statusExName;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("事件轨迹集")
    public static class TraceItem {
        @ApiModelProperty("状态")
        private Integer status;

        @ApiModelProperty("状态名称")
        private String statusName;

        @ApiModelProperty("描述")
        private String desc;

        @ApiModelProperty("时间")
        private String time;

        @ApiModelProperty("所在城市")
        private String location;
    }

    public void handleData() {
        this.traceList = JSON.parseArray(this.traceJson, TraceItem.class);
    }

}
