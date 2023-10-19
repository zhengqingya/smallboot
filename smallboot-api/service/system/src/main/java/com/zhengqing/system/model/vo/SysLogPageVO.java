package com.zhengqing.system.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p>系统管理-操作日志-分页列表-响应参数</p>
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
@ApiModel("系统管理-操作日志-分页列表-响应参数")
public class SysLogPageVO extends BaseVO {

    @ApiModelProperty("主键ID")
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

    @ApiModelProperty("操作时间")
    private Date createTime;

    public void handleData() {

    }

}
