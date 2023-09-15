package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import com.zhengqing.mall.config.mybatis.handler.ShopListOpenTimeTypeHandler;
import com.zhengqing.mall.model.bo.SmsShopOpenTimeBO;
import com.zhengqing.mall.model.enums.SmsShopTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>  商城-店铺信息 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/13 09:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sms_shop")
@ApiModel("商城-店铺信息")
public class SmsShop extends BaseEntity<SmsShop> {

    // ↓↓↓↓↓↓ 基本信息 =======================

    @ApiModelProperty("门店ID")
    @TableId(value = "shop_id", type = IdType.AUTO)
    private Integer shopId;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("门店名称")
    private String shopName;

//    @ApiModelProperty("省代码")
//    private Integer provinceCode;
//
//    @ApiModelProperty("市代码")
//    private Integer cityCode;
//
//    @ApiModelProperty("区代码")
//    private Integer areaCode;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty("门店详细地址")
    private String address;

    @ApiModelProperty("门店坐标-经度")
    private BigDecimal longitude;

    @ApiModelProperty("门店坐标-纬度")
    private BigDecimal latitude;

    /**
     * {@link SmsShopTypeEnum}
     */
    @ApiModelProperty("店铺类型（1:餐饮 2:电商 3:教育）")
    private Integer type;

    @ApiModelProperty("联系人")
    private String contactName;

    @ApiModelProperty("联系手机号")
    private String contactPhone;

    @ApiModelProperty("是否显示（1：是 0：否）")
    private Boolean isShow;

    // ↓↓↓↓↓↓ 营业信息 =======================

    @ApiModelProperty("堂食状态（1：开启 0：关闭）")
    private Boolean snackStatus;

    @ApiModelProperty("外卖状态（1：开启 0：关闭）")
    private Boolean takeoutStatus;

    @ApiModelProperty("门店营业状态（1：营业中 0：未营业）")
    private Boolean openStatus;

    @ApiModelProperty("营业时间")
    @TableField(typeHandler = ShopListOpenTimeTypeHandler.class)
    private List<SmsShopOpenTimeBO> openTimeList;

    // ↓↓↓↓↓↓ 外卖信息 =======================

//    @ApiModelProperty("外卖配送费（满?分,配送费?分）")
//    private JSONArray deliverFeeList;

    @ApiModelProperty("外卖配送距离（单位：米）")
    private Integer deliverDistance;

//    @ApiModelProperty("外卖配送范围")
//    private JSONArray deliverScopeList;

}
