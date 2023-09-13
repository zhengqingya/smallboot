package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p> 商城-店铺信息-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/13 09:51
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-店铺信息-分页列表-请求参数")
public class SmsShopPageDTO extends BaseDTO {

    @ApiModelProperty("门店ID")
    private Integer shopId;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("门店名称")
    private String shopName;

    @ApiModelProperty("省编码")
    private Integer provinceCode;

    @ApiModelProperty("市编码")
    private Integer cityCode;

    @ApiModelProperty("区编码")
    private Integer areaCode;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty("门店详细地址")
    private String address;

    @ApiModelProperty("店铺类型（1:餐饮 2:电商 3:教育）")
    private Byte type;

    @ApiModelProperty("联系人")
    private String contactName;

    @ApiModelProperty("联系手机号")
    private String contactPhone;

    @ApiModelProperty("门店坐标-经度")
    private String longitude;

    @ApiModelProperty("门店坐标-纬度")
    private String latitude;

    @ApiModelProperty("外卖配送费（满?分,配送费?分）")
    private String deliverFeeJson;

    @ApiModelProperty("外卖配送距离（单位：米）")
    private Integer deliverDistance;

    @ApiModelProperty("外卖配送范围")
    private String deliverScopeJson;

    @ApiModelProperty("是否显示（1：是 0：否）")
    private Byte isShow;

    @ApiModelProperty("堂食状态（1：开启 0：关闭）")
    private Byte snackStatus;

    @ApiModelProperty("外卖状态（1：开启 0：关闭）")
    private Byte takeoutStatus;

    @ApiModelProperty("门店营业状态（1：营业中 0：未营业）")
    private Byte openStatus;

    @ApiModelProperty("营业时间")
    private String openTimeJson;

    @ApiModelProperty("一天中开始营业时间点")
    private String startTime;

    @ApiModelProperty("一天中结束营业时间点")
    private String endTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建人id")
    private Long createBy;

    @ApiModelProperty("更新人id")
    private Long updateBy;

    @ApiModelProperty("是否删除(0->否,1->是)")
    private Byte isDeleted;

}