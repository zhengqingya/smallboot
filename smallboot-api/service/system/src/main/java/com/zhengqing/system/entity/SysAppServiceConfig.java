package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  系统管理-小程序服务商平台配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/20 16:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_app_service_config")
@ApiModel("系统管理-小程序服务商平台配置")
public class SysAppServiceConfig extends BaseEntity<SysAppServiceConfig> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("第三方小程序应用appid")
    private String componentAppId;

    @ApiModelProperty("第三方小程序应用appsecret")
    private String componentAppSecret;

    @ApiModelProperty("消息验证TOKEN")
    private String tpToken;

    @ApiModelProperty("消息加密解密KEY")
    private String encodingAesKey;

}
