package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 系统管理-租户信息-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 15:40
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-租户信息-分页列表-请求参数")
public class SysTenantPageDTO extends BaseDTO {

    @ApiModelProperty("租户名")
    private String name;

}
