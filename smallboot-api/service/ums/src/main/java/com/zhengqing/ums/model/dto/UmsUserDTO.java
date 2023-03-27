package com.zhengqing.ums.model.dto;

import cn.hutool.core.lang.Assert;
import com.zhengqing.common.base.exception.ParameterException;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.parameter.ParamCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 用户信息-查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 10:48
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class UmsUserDTO extends BaseDTO implements ParamCheck {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("微信openid")
    private String openid;

    @ApiModelProperty("手机号码")
    private String phone;

    @Override
    public void checkParam() throws ParameterException {
        Assert.isFalse(
                this.userId == null
                        && StringUtils.isBlank(this.username)
                        && StringUtils.isBlank(this.openid)
                        && StringUtils.isBlank(this.phone)
                , "用户查询条件丢失！"
        );
    }

}
