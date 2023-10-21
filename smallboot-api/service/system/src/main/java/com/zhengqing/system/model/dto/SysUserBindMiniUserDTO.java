package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p> 系统用户绑定小程序用户 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 11:15
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统用户绑定小程序用户")
public class SysUserBindMiniUserDTO extends BaseDTO {

    @ApiModelProperty(value = "true:绑定 false:解绑")
    @NotNull(message = "是否绑定不能为空")
    private Boolean isBind;

    @ApiModelProperty(value = "系统用户id")
    @NotNull(message = "系统用户id不能为空")
    private Integer userId;

    @ApiModelProperty(value = "小程序用户id")
    @NotNull(message = "小程序用户id不能为空")
    private Long miniUserId;

}
