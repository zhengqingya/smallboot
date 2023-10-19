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
 * <p> 系统管理-操作日志-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/19 16:32
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-操作日志-分页列表-请求参数")
public class SysLogPageDTO extends BasePageDTO {

    /**
     * {@link com.zhengqing.system.enums.SysLogTypeEnum}
     */
    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("请求方法")
    private String apiMethod;

    @ApiModelProperty("请求方法名")
    private String apiMethodName;

    @ApiModelProperty("操作人名称")
    private String operationName;

    @ApiModelProperty("请求url")
    private String requestUrl;

}
