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
 * <p>系统管理-租户信息-分页列表-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 15:40
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-租户信息-分页列表-响应参数")
public class SysTenantPageVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("租户名")
    private String name;

    @ApiModelProperty("管理员")
    private String adminName;

    @ApiModelProperty("管理员手机号")
    private String adminPhone;

    @ApiModelProperty("租户状态(0->停用 1->开启)")
    private Integer status;

    @ApiModelProperty("过期时间")
    private Date expireTime;

    @ApiModelProperty("账号数量")
    private Integer accountCount;

    @ApiModelProperty("租户套餐ID")
    private Integer packageId;

    @ApiModelProperty("租户套餐名称")
    private String packageName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public void handleData() {

    }

}
