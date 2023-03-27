package com.zhengqing.mall.web.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>商城-分类关联商品-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 16:04
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-分类关联商品-响应参数")
public class WebPmsCategorySpuRelationListVO extends BaseVO {

    @ApiModelProperty("分类id")
    private String categoryId;

    @ApiModelProperty("商品id")
    private String spuId;

}
