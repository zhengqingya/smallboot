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
 * <p>商城-属性key-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/20 17:38
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-属性key-响应参数")
public class WebPmsAttrKeyListVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("属性key名称")
    private String attrKeyName;

    @ApiModelProperty("排序")
    private Integer sort;

}
