package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p> 商城-店铺信息-保存-提交参数 </p>
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
@ApiModel("商城-店铺信息-保存-提交参数")
public class WebSmsShopSaveDTO extends BaseDTO {

    // ↓↓↓↓↓↓ 基本信息 =======================

    @ApiModelProperty("门店ID")
    @NotNull(groups = {UpdateGroup.class}, message = "门店ID不能为空!")
    private Integer shopId;

    @NotBlank(message = "门店名称不能为空！")
    @ApiModelProperty("门店名称")
    private String shopName;

    @NotEmpty(message = "门店头图不能为空！")
    @ApiModelProperty("门店头图")
    private List<MallFileBO> headImgList;

    @NotBlank(message = "省名称不能为空！")
    @ApiModelProperty("省名称")
    private String provinceName;

    @NotBlank(message = "市名称不能为空！")
    @ApiModelProperty("市名称")
    private String cityName;

    @NotBlank(message = "区名称不能为空！")
    @ApiModelProperty("区名称")
    private String areaName;

    @NotBlank(message = "门店详细地址不能为空！")
    @ApiModelProperty("门店详细地址")
    private String address;

    @NotNull(message = "门店坐标-经度不能为空！")
    @ApiModelProperty("门店坐标-经度")
    private Double longitude;

    @NotNull(message = "门店坐标-纬度不能为空！")
    @ApiModelProperty("门店坐标-纬度")
    private Double latitude;

    /**
     * {@link SmsShopTypeEnum}
     */
    @NotNull(message = "店铺类型不能为空！")
    @ApiModelProperty("店铺类型（1:餐饮 2:电商 3:教育）")
    private Integer type;

    @NotBlank(message = "联系人不能为空！")
    @ApiModelProperty("联系人")
    private String contactName;

    @NotBlank(message = "联系手机号不能为空！")
    @ApiModelProperty("联系手机号")
    private String contactPhone;

    @NotNull(message = "是否显示不能为空！")
    @ApiModelProperty("是否显示（1：是 0：否）")
    private Boolean isShow;

    // ↓↓↓↓↓↓ 营业信息 =======================

    @NotNull(message = "堂食状态不能为空！")
    @ApiModelProperty("堂食状态（1：开启 0：关闭）")
    private Boolean snackStatus;

    @NotNull(message = "外卖状态不能为空！")
    @ApiModelProperty("外卖状态（1：开启 0：关闭）")
    private Boolean takeoutStatus;

    @NotNull(message = "门店营业状态不能为空！")
    @ApiModelProperty("门店营业状态（1：营业中 0：未营业）")
    private Boolean openStatus;

    @Valid
    @NotEmpty(message = "营业时间不能为空！")
    @ApiModelProperty("营业时间")
    private List<SmsShopOpenTimeBO> openTimeList;

    // ↓↓↓↓↓↓ 外卖信息 =======================

    @NotNull(message = "外卖配送距离不能为空！")
    @ApiModelProperty("外卖配送距离（单位：米）")
    private Integer deliverDistance;

//    @ApiModelProperty("外卖配送范围")
//    private JSONArray deliverScopeList;

//    @ApiModelProperty("外卖配送费（满?分,配送费?分）")
//    private JSONArray deliverFeeList;


}
