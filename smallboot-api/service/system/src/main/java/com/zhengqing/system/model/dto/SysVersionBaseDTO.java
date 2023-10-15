package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 系统管理-版本记录-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/15 14:58
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-版本记录-分页列表-请求参数")
public class SysVersionBaseDTO extends BasePageDTO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;


}
