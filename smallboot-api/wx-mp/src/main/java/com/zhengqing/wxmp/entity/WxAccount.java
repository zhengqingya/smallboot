package com.zhengqing.wxmp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  微信公众号-账号 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_wx_account")
@ApiModel("微信公众号-账号")
public class WxAccount extends BaseEntity<WxAccount> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    /**
     * {@link com.zhengqing.wxmp.enums.WxAccountTypeEnum}
     */
    @ApiModelProperty("账号类型（1：测试号；2：服务号；3：订阅号）")
    private Byte type;

    @ApiModelProperty("公众号ID")
    private String account;

    @ApiModelProperty("AppID")
    private String appId;

    @ApiModelProperty("密钥")
    private String appSecret;

    @ApiModelProperty("服务器地址")
    private String url;

    @ApiModelProperty("令牌")
    private String token;

    @ApiModelProperty("消息加密密钥")
    private String aesKey;

    @ApiModelProperty("二维码地址")
    private String qrCodeUrl;

}
