package com.zhengqing.system.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.system.enums.SysAppStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p>系统管理-商户管理-分页列表-响应参数</p>
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
@ApiModel("系统管理-商户管理-分页列表-响应参数")
public class SysMerchantPageVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("状态(0:停用 1:正常)")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    /**
     * {@link com.zhengqing.system.enums.SysMerchantTypeEnum}
     */
    @ApiModelProperty("商户类型")
    private Integer type;
    @ApiModelProperty("过期时间")
    private Date expireTime;

    @ApiModelProperty("最大用户数")
    private Integer userNum;

    @ApiModelProperty("最大职位发布数")
    private Integer jobNum;

    @ApiModelProperty("AppID")
    private String appId;

    @ApiModelProperty("AppSecret")
    private String appSecret;

    /**
     * {@link SysAppStatusEnum}
     */
    @ApiModelProperty("小程序状态")
    private Integer appStatus;

    @ApiModelProperty("小程序首页标题")
    private String appIndexTitle;

    public void handleData() {

    }

}
