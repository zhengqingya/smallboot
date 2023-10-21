package com.zhengqing.system.model.dto;

import cn.hutool.core.lang.Assert;
import com.zhengqing.common.base.exception.ParameterException;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.parameter.CheckParam;
import com.zhengqing.common.core.custom.parameter.HandleParam;
import com.zhengqing.system.enums.SysAppStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p> 小程序批量操作-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/20 16:11
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("小程序批量操作-提交参数")
public class SysAppOperationDTO extends BaseDTO implements HandleParam, CheckParam {

    @ApiModelProperty(value = "appId", example = "[1]")
    private List<String> appIdList;

    /**
     * {@link SysAppStatusEnum}
     */
    @NotNull(message = "小程序状态类型不能为空")
    @ApiModelProperty("小程序状态")
    private Integer appStatus;

    @ApiModelProperty("提交代码描述")
    private String uploadCodeDesc;

    @ApiModelProperty("模板id")
    private Integer templateId;

    @ApiModelProperty("版本号")
    private String version;

    @Override
    public void handleParam() {

    }

    @Override
    public void checkParam() throws ParameterException {
        if (SysAppStatusEnum.提交代码.getStatus().equals(this.appStatus)) {
            Assert.notNull(this.templateId, "模板id不能为空！");
            Assert.notBlank(this.uploadCodeDesc, "提交代码描述不能为空！");
            Assert.notBlank(this.version, "版本号不能为空！");
        }
    }
}
