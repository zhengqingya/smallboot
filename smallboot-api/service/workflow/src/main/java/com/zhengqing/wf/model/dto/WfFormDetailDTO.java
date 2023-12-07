package com.zhengqing.wf.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 工作流-表单-详情-请求参数 </p>
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
@ApiModel("工作流-表单-详情-请求参数")
public class WfFormDetailDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    private Long id;


}
