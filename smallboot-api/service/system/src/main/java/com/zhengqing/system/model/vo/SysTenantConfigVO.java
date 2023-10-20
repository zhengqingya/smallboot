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
 * <p>系统管理-租户信息-小程序查看配置-响应参数</p>
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
@ApiModel("系统管理-租户信息-小程序查看配置-响应参数")
public class SysTenantConfigVO extends BaseVO {

    @ApiModelProperty("租户名")
    private String name;

    @ApiModelProperty("租户状态(0->停用 1->开启)")
    private Integer status;

    @ApiModelProperty("过期时间")
    private Date expireTime;

}
