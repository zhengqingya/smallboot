package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.system.enums.SysConfigTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p> 系统管理-系统配置-分页列表-提交参数 </p>
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
public class SysConfigPageDTO extends BaseDTO {

    @ApiModelProperty("属性key")
    private String key;

    /**
     * {@link SysConfigTypeEnum}
     */
    @NotNull(message = "类型不能为空！")
    @ApiModelProperty("类型（1:配置 2:属性）")
    private Integer type;

}
