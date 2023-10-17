package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.system.enums.SysAppTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 系统管理-商户管理-列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-商户管理-列表-请求参数")
public class SysMerchantListDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    /**
     * {@link SysAppTypeEnum}
     */
    @ApiModelProperty("商户类型")
    private Integer type;

}
