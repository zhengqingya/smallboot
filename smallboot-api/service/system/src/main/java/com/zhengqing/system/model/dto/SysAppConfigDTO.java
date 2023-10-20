package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BasePageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
public class SysAppConfigDTO extends BasePageDTO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("租户ids")
    private List<Integer> tenantIdList;

    @ApiModelProperty("AppID")
    private List<String> appIdList;

    @ApiModelProperty("关联的租户是否没有限制，可以正常使用小程序配置(AppId不为空！)")
    private Boolean isUsable;

}
