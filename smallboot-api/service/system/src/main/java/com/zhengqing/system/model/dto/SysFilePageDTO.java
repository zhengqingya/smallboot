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
 * <p> 系统管理-文件上传记录-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/28 11:24
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-文件上传记录-分页列表-请求参数")
public class SysFilePageDTO extends BaseDTO {

    @ApiModelProperty("文件名")
    private String name;

}
