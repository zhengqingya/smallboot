package com.zhengqing.mall.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p> 商城-店铺信息-营业时间 </p>
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
@ApiModel("商城-店铺信息-营业时间")
public class SmsShopOpenTimeBO extends BaseBO {

    @ApiModelProperty("营业时间(1~7)")
    private List<Integer> weekList;

    @ApiModelProperty("一天中开始营业时间点(HH:mm)")
    private String startTime;

    @ApiModelProperty("一天中结束营业时间点(HH:mm)")
    private String endTime;

}
