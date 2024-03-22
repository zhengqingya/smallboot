package com.zhengqing.mall.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>商城-分类-响应参数</p>
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
@ApiModel("web-商城-分类-响应参数")
public class WebPmsCategoryBaseVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("父分类id")
    private Long parentId;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("是否显示(false->否,true->是)")
    private Boolean isShow;

    @ApiModelProperty("子分类")
    private List<WebPmsCategoryBaseVO> children;

}
