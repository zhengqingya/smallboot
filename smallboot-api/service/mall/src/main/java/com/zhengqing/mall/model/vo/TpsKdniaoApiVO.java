package com.zhengqing.mall.model.vo;

import com.zhengqing.mall.model.enums.TpsLogisticsStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>
 * 快递鸟-物流-响应参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/23 9:19 下午
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("快递鸟-物流-响应参数")
public class TpsKdniaoApiVO {

    /**
     * {@link TpsLogisticsStatusEnum }
     * 增值物流状态：
     * 0-暂无轨迹信息
     * 1-已揽收
     * 2-在途中
     * 201-到达派件城市, 202-派件中, 211-已放入快递柜或驿站,
     * 3-已签收
     * 301-正常签收, 302-派件异常后最终签收, 304-代收签收, 311-快递柜或驿站签收,
     * 4-问题件
     * 401-发货无信息, 402-超时未签收, 403-超时未更新, 404-拒收(退件), 405-派件异常, 406-退货签收, 407-退货未签收, 412-快递柜或驿站超时未取
     */
    @ApiModelProperty("增值物流状态")
    private Integer StateEx;

    @ApiModelProperty("快递单号")
    private String LogisticCode;

    @ApiModelProperty("快递公司编码")
    private String ShipperCode;

    @ApiModelProperty("失败原因")
    private String Reason;

    @ApiModelProperty("事件轨迹集")
    private List<TraceItem> Traces;

    /**
     * {@link TpsLogisticsStatusEnum }
     */
    @ApiModelProperty("物流状态：0-暂无轨迹信息,1-已揽收,2-在途中,3-签收,4-问题件")
    private Integer State;

    @ApiModelProperty("用户ID")
    private String EBusinessID;

    @ApiModelProperty("送货人")
    private String DeliveryMan;

    @ApiModelProperty("送货人电话号码")
    private String DeliveryManTel;

    @ApiModelProperty("成功与否 true/false")
    private String Success;

    @ApiModelProperty("所在城市")
    private String Location;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("事件轨迹集")
    public static class TraceItem {
        /**
         * {@link TpsLogisticsStatusEnum }
         */
        @ApiModelProperty("当前状态(同StateEx)")
        private Integer Action;

        @ApiModelProperty("描述")
        private String AcceptStation;

        @ApiModelProperty("时间")
        private String AcceptTime;

        @ApiModelProperty("所在城市")
        private String Location;
    }

}
