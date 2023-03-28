package com.zhengqing.mall.model.vo;

import com.google.common.collect.Lists;
import com.zhengqing.mall.model.enums.TpsLogisticsStatusEnum;
import com.zhengqing.mall.model.enums.TpsLogisticsCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p> 第三方-通用物流-参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/26 10:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("第三方-通用物流-参数")
public class TpsLogisticsVO {

    /**
     * {@link TpsLogisticsCodeEnum }
     */
    @ApiModelProperty("快递公司编码")
    private String logisticsCode;

    @ApiModelProperty("快递单号")
    private String logisticsNo;

    @ApiModelProperty("事件轨迹集")
    private List<TraceItem> traceList;

    @ApiModelProperty("送货人名称")
    private String delivererName;

    @ApiModelProperty("送货人电话号码")
    private String delivererPhone;

    @ApiModelProperty("所在城市")
    private String location;

    /**
     * {@link TpsLogisticsStatusEnum }
     */
    @ApiModelProperty("物流状态")
    private Integer status;

    @ApiModelProperty("状态名称")
    private String statusName;

    /**
     * {@link TpsLogisticsStatusEnum }
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
        this.statusName = TpsLogisticsStatusEnum.getEnum(this.status).getDesc();
        this.statusExName = TpsLogisticsStatusEnum.getEnum(this.statusEx).getDesc();
        if (CollectionUtils.isEmpty(this.traceList)) {
            this.traceList = Lists.newArrayList();
        }
        this.traceList.forEach(item -> {
            item.statusName = TpsLogisticsStatusEnum.getEnum(item.status).getDesc();
        });
    }

}
