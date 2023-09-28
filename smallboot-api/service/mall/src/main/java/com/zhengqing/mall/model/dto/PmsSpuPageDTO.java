package com.zhengqing.mall.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.dto.BasePageDTO;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.core.custom.parameter.CheckParam;
import com.zhengqing.mall.enums.PmsSpuTabEnum;
import com.zhengqing.mall.enums.PmsSpuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p> 商城-商品分页列表-查询参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-商品分页列表-查询参数")
public class PmsSpuPageDTO extends BasePageDTO implements CheckParam {

    /**
     * {@link PmsSpuTabEnum}
     */
    @ApiModelProperty("tab条件")
    private Byte tabValue;

    @JsonIgnore
    @ApiModelProperty(value = "是否上架(0->下架；1->上架)", hidden = true)
    private Boolean isPut;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty(value = "是否预售(false->否；true->是)", example = "true")
    private Boolean isPresell;

    @ApiModelProperty(value = "预售开始时间", example = "2021-08-25 09:00:00")
    @DateTimeFormat(pattern = MyDateUtil.DATE_TIME_FORMAT, iso = DateTimeFormat.ISO.DATE_TIME)
    private Date presellStartTime;

    @ApiModelProperty(value = "预售结束时间", example = "2021-08-26 23:59:59")
    @DateTimeFormat(pattern = MyDateUtil.DATE_TIME_FORMAT, iso = DateTimeFormat.ISO.DATE_TIME)
    private Date presellEndTime;

    /**
     * {@link PmsSpuTypeEnum}
     */
    @ApiModelProperty(value = "类型", example = "101")
    private Integer type;

    @Override
    public void checkParam() {
        if (this.tabValue != null) {
            PmsSpuTabEnum spuTabEnum = PmsSpuTabEnum.getEnum(this.tabValue);
            switch (spuTabEnum) {
                case ALL:
                    this.isPut = null;
                    break;
                case ON:
                    this.isPut = true;
                    break;
                case OFF:
                    this.isPut = false;
                    break;
                default:
                    break;
            }
        }

        if (this.isPresell != null && !this.isPresell) {
            this.presellStartTime = null;
            this.presellEndTime = null;
        }
    }

}
