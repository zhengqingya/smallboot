package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  系统管理-操作日志 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/19 16:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_log")
@ApiModel("系统管理-操作日志")
public class SysLog extends BaseEntity<SysLog> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

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
