package com.zhengqing.mall.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>商城-分类-分类关联商品-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 14:01
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-分类关联商品-分页列表-响应参数")
public class WebPmsCategorySpuRelationPageVO extends BaseVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("分类id")
    private String categoryId;

    @ApiModelProperty("商品ID")
    private String spuId;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("是否显示(false->否,true->是)")
    private Boolean isShow;

    @ApiModelProperty("是否上架：false->下架；true->上架")
    private Boolean isPut;

}
