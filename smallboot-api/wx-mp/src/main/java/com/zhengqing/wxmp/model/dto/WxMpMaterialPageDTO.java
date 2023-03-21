package com.zhengqing.wxmp.model.dto;

import cn.hutool.core.lang.Assert;
import com.zhengqing.common.base.exception.ParameterException;
import com.zhengqing.common.base.model.dto.BasePageDTO;
import com.zhengqing.common.core.custom.parameter.ParamCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.chanjar.weixin.common.api.WxConsts;

import javax.validation.constraints.NotBlank;

/**
 * <p> 微信公众号-素材管理-分页列表-请求参数 </p>
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
@ApiModel("微信公众号-素材管理-分页列表-请求参数")
public class WxMpMaterialPageDTO extends BasePageDTO implements ParamCheck {

    @NotBlank(message = "AppID不能为空！")
    @ApiModelProperty("AppID")
    private String appId;

    /**
     * {@link WxConsts.MaterialType}
     */
    @NotBlank(message = "素材类型不能为空！")
    @ApiModelProperty("素材类型")
    private String type;

    @Override
    public void checkParam() throws ParameterException {
        Assert.isTrue(super.getPageSize() <= 20, "分页数最大为20！");
    }

}
