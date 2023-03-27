package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p>  商城-物流信息表 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/26 15:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oms_logistic")
@ApiModel("商城-物流信息表")
public class OmsLogistic extends Model<OmsLogistic> {

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("物流公司")
    private String logisticsCompany;

    /**
     * {@link com.zhengqing.mall.common.model.enums.TpsLogisticsCodeEnum }
     */
    @ApiModelProperty("快递公司编码")
    private String logisticsCode;

    @ApiModelProperty("物流单号")
    private String logisticsNo;

    @ApiModelProperty("收货人电话（SF快递查询物流必用字段）")
    private String receiverPhone;

    @ApiModelProperty("事件轨迹集")
    private String traceJson;

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

    /**
     * {@link com.zhengqing.mall.common.model.enums.TpsLogisticsStatusEnum }
     */
    @ApiModelProperty("增值物流状态")
    private Integer statusEx;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
