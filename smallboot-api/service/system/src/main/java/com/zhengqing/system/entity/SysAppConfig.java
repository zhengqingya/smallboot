package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import com.zhengqing.common.sdk.douyin.service.model.vo.DyServiceVersionVO;
import com.zhengqing.system.config.mybatis.handler.SystemJsonAppVersionHandler;
import com.zhengqing.system.enums.SysAppStatusEnum;
import com.zhengqing.system.enums.SysAppTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  系统管理-小程序配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/17 21:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_sys_app_config", autoResultMap = true)
@ApiModel("系统管理-小程序配置")
public class SysAppConfig extends IsDeletedBaseEntity<SysAppConfig> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField(typeHandler = SystemJsonAppVersionHandler.class)
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
