package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
@ApiModel("mini-商城-商品分页列表-查询参数")
public class MiniPmsSpuPageDTO extends BasePageDTO {

    @ApiModelProperty("名称")
    private String name;

}
