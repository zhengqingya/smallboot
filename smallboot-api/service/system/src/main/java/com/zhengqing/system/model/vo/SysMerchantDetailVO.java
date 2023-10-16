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
 * <p>系统管理-商户管理-详情-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-商户管理-详情-响应参数")
public class SysMerchantDetailVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    /**
     * {@link com.zhengqing.system.enums.SysMerchantTypeEnum}
     */
    @ApiModelProperty("商户类型")
    private Integer type;

    @ApiModelProperty("过期时间")
    private Date expireTime;

    @ApiModelProperty("AppID")
    private String appId;

    @ApiModelProperty("AppSecret")
    private String appSecret;

    @ApiModelProperty("小程序首页标题")
    private String appIndexTitle;

    public void handleData() {

    }

}
