package com.zhengqing.wf.model.dto;

import com.zhengqing.common.base.model.dto.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 工作流-流程模型-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("工作流-流程模型-分页列表-请求参数")
public class WfModelPageDTO extends BasePageDTO {

    @ApiModelProperty("流程标识-精准匹配")
    private String key;

    @ApiModelProperty("名称-模糊匹配")
    private String name;

}
