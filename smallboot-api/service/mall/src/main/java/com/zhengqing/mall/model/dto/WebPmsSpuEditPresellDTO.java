package com.zhengqing.mall.model.dto;

import cn.hutool.core.lang.Assert;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.parameter.CheckParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * <p> 商城-商品预售-保存参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/20 16:11
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-商品预售-保存参数")
public class WebPmsSpuEditPresellDTO extends BaseDTO implements CheckParam {

    @NotEmpty(message = "商品不能为空")
    @ApiModelProperty(value = "商品ids", required = true, example = "[1]")
    private List<String> idList;

    @NotNull(message = "是否预售不能为空！")
    @ApiModelProperty(value = "是否预售(false->否；true->是)", required = true, example = "true")
    private Boolean isPresell;

    @ApiModelProperty(value = "预售开始时间", example = "2021-08-25 00:00:00")
    private Date presellStartTime;

    @ApiModelProperty(value = "预售结束时间", example = "2021-10-25 23:59:59")
    private Date presellEndTime;

    @ApiModelProperty(value = "预售-发货日期(购买之后？天之后发货)", example = "15")
    private Integer presellDeliverDay;

    @Override
    public void checkParam() {
        if (this.isPresell) {
            Assert.notNull(this.presellStartTime, "预售开始时间不能为空!");
            Assert.notNull(this.presellEndTime, "预售结束时间不能为空!");
//            Assert.notNull(presellDeliverDay, "预售发货日期不能为空!");
        }
    }
}
