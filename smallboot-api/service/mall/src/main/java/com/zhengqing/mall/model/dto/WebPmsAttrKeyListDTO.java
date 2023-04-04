package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.parameter.HandleParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> 商城-属性key-列表-请求参数 </p>
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
@ApiModel("web-商城-属性key-列表-请求参数")
public class WebPmsAttrKeyListDTO extends BaseDTO implements HandleParam {

    @ApiModelProperty("属性key名称")
    private String attrKeyName;

    @ApiModelProperty("ids")
    private List<Long> idList;

    @Override
    public void handleParam() {
        if (!CollectionUtils.isEmpty(this.idList)) {
            // 去重
            this.idList = this.idList.stream().distinct().collect(Collectors.toList());
        }
    }
}
