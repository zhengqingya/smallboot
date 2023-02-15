package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * <p> 系统管理-系统属性-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-系统属性-保存-提交参数")
public class SysPropertySaveDTO extends BaseDTO {

    @ApiModelProperty(value = "主键ID", example = "1")
    private Integer id;

    @NotBlank(message = "属性key不能为空!")
    @ApiModelProperty(value = "属性key", required = true, example = "hello")
    private String key;

    @NotBlank(message = "属性value不能为空!")
    @ApiModelProperty(value = "属性value", required = true, example = "world")
    private String value;

    @NotBlank(message = "备注不能为空!")
    @ApiModelProperty(value = "备注", example = "this is remark!")
    private String remark;

}
