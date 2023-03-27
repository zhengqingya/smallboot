package com.zhengqing.system.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> oauth客户端-响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/06/10 16:25
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysOauthClientVO extends BaseVO {

    @ApiModelProperty("客户端ID，唯一标识")
    private String clientId;

    @ApiModelProperty("客户端访问秘钥，BCryptPasswordEncoder加密算法加密")
    private String clientSecret;

    @ApiModelProperty("可访问资源id(英文逗号分隔)")
    private String resourceIds;

    @ApiModelProperty("授权范围(英文逗号分隔)")
    private String scope;

    @ApiModelProperty("授权类型(英文逗号分隔)")
    private String authorizedGrantTypes;

    @ApiModelProperty("重定向uri")
    private String webServerRedirectUri;

    @ApiModelProperty("@PreAuthorize(\"hasAuthority('admin')\")可以在方法上标志 用户或者说client 需要说明样的权限\n" +
            "指定客户端所拥有的Spring Security的权限值\n" +
            "(英文逗号分隔)")
    private String authorities;

    @ApiModelProperty("令牌有效期(单位:秒)")
    private Integer accessTokenValidity;

    @ApiModelProperty("刷新令牌有效期(单位:秒)")
    private Integer refreshTokenValidity;

    @ApiModelProperty("预留字段,在Oauth的流程中没有实际的使用(JSON格式数据)")
    private String additionalInformation;

    @ApiModelProperty("设置用户是否自动Approval操作, 默认值为 'false' 可选值包括 'true','false', 'read','write'.\n" +
            "该字段只适用于grant_type=\"authorization_code\"的情况,当用户登录成功后,若该值为'true'或支持的scope值,则会跳过用户Approve的页面, 直接授权")
    private String autoapprove;

}
