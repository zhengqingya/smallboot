package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p> 系统管理-操作日志-保存-提交参数 </p>
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
@ApiModel("系统管理-操作日志-保存-提交参数")
public class SysLogSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Long id;

    /**
     * {@link com.zhengqing.system.enums.SysLogTypeEnum}
     */
    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("请求方法")
    private String apiMethod;

    @ApiModelProperty("请求方法名")
    private String apiMethodName;

    @ApiModelProperty("请求头参数")
    private String apiHeader;

    @ApiModelProperty("操作人名称")
    private String operationName;

    @ApiModelProperty("请求IP")
    private String requestIp;

    @ApiModelProperty("请求url")
    private String requestUrl;

    @ApiModelProperty("请求方式")
    private String requestHttpMethod;

    @ApiModelProperty("请求参数")
    private String requestParams;

    @ApiModelProperty("服务器环境")
    private String env;

    @ApiModelProperty("执行时间(单位：毫秒)")
    private Integer time;

    @ApiModelProperty("状态(0:异常 1:正常)")
    private Integer status;

    @ApiModelProperty("响应结果")
    private String responseResult;
    
}
