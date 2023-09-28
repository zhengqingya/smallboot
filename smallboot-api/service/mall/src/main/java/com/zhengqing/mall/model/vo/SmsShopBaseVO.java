package com.zhengqing.mall.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.mall.config.mybatis.handler.ShopListOpenTimeTypeHandler;
import com.zhengqing.mall.model.bo.MallFileBO;
import com.zhengqing.mall.model.bo.SmsShopOpenTimeBO;
import com.zhengqing.mall.enums.SmsShopTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>商城-店铺信息-响应参数</p>
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
@ApiModel("商城-店铺信息-响应参数")
public class SmsShopBaseVO extends BaseVO {


    // ↓↓↓↓↓↓ 基本信息 =======================

    @ApiModelProperty("门店ID")
    @NotNull(groups = {UpdateGroup.class}, message = "门店ID不能为空!")
    private Integer shopId;

    @ApiModelProperty("门店名称")
    private String shopName;

    @ApiModelProperty("门店头图")
    private List<MallFileBO> headImgList;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty("门店详细地址")
    private String address;

    @ApiModelProperty("门店坐标-经度")
    private Double longitude;

    @ApiModelProperty("门店坐标-纬度")
    private Double latitude;

    @ApiModelProperty("门店距离 单位：m（查询时传入经纬度才会计算此字段值）")
    private Double distance;

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

    @ApiModelProperty("外卖配送距离（单位：米）")
    private Integer deliverDistance;

//    @ApiModelProperty("外卖配送范围")
//    private String deliverScopeList;

//    @ApiModelProperty("外卖配送费（满?分,配送费?分）")
//    private String deliverFeeList;


    public void handleData() {

    }

}
