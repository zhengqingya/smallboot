package com.zhengqing.mall.mini.model.dto;

import com.zhengqing.common.base.model.dto.BasePageDTO;
import com.zhengqing.mall.common.model.enums.PmsSpuRateTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 商城-商品评价-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 17:10
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商城-商品评价-分页列表-请求参数")
public class MiniPmsSpuRatePageDTO extends BasePageDTO {

    @ApiModelProperty("商品id")
    private String spuId;

    @ApiModelProperty("是否含有图片(false->否,true->是)")
    private Boolean isImg;

    @ApiModelProperty("是否含有视频(false->否,true->是)")
    private Boolean isVideo;

    /**
     * {@link PmsSpuRateTypeEnum}
     */
    @ApiModelProperty("评价分类(1->好评,2->差评,3->一般)")
    private Byte rateType;

}
