package com.zhengqing.system.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import com.zhengqing.common.sdk.douyin.service.model.vo.DyServiceVersionVO;
import com.zhengqing.system.enums.SysAppStatusEnum;
import com.zhengqing.system.enums.SysAppTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 系统管理-小程序配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 18:10
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-小程序配置")
public class SysAppConfigBO extends BaseBO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("AppID")
    private String appId;

    @ApiModelProperty("AppSecret")
    private String appSecret;

    @ApiModelProperty("小程序版本")
    private String appVersion;

    @ApiModelProperty("小程序版本详情信息")
    private DyServiceVersionVO.Data appVersionObj;

    /**
     * {@link SysAppStatusEnum}
     */
    @ApiModelProperty("小程序状态")
    private Integer appStatus;

    @ApiModelProperty("小程序首页标题")
    private String appIndexTitle;

    /**
     * {@link SysAppTypeEnum}
     */
    @ApiModelProperty("小程序类型")
    private Integer appType;

}
