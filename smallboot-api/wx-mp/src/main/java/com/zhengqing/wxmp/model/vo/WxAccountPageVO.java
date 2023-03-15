package com.zhengqing.wxmp.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.wxmp.enums.WxAccountTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>微信公众号-账号-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("微信公众号-账号-响应参数")
public class WxAccountPageVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    /**
     * {@link com.zhengqing.wxmp.enums.WxAccountTypeEnum}
     */
    @ApiModelProperty("账号类型（1：测试号；2：服务号；3：订阅号）")
    private Byte type;

    @ApiModelProperty("账号类型名称")
    private String typeName;

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

    public void handleData() {
        this.typeName = WxAccountTypeEnum.getEnum(this.type).getDesc();
    }

}
