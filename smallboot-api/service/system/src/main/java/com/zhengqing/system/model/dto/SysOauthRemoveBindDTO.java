package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 系统管理 - 用户三方授权移除绑定参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/12/6 14:54
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理 - 用户三方授权移除绑定参数")
public class SysOauthRemoveBindDTO extends BaseDTO {

    // @ApiModelProperty(value = "用户id")
    // private Integer userId;

    @ApiModelProperty(value = "主键id")
    private Integer userReOauthId;

}
