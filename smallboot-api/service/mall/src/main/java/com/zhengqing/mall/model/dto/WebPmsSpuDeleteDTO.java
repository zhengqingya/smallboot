package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p> web-商城-商品删除-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/11 11:57
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-商品删除-提交参数")
public class WebPmsSpuDeleteDTO extends BaseDTO {

    @NotEmpty(message = "商品不能为空")
    @ApiModelProperty(value = "商品ids", required = true, example = "1")
    private List<String> idList;

}
