package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 系统管理-系统属性-分页列表-提交参数 </p>
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
public class SysPropertyPageDTO extends BaseDTO {

    @ApiModelProperty("属性key")
    private String key;

}
