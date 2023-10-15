package com.zhengqing.system.model.dto;

import cn.hutool.core.lang.Assert;
import com.zhengqing.common.base.exception.ParameterException;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.parameter.CheckParam;
import com.zhengqing.common.core.custom.parameter.HandleParam;
import com.zhengqing.system.enums.SysMerchantAppStatusEnum;
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
 * <p> 系统管理-商户管理-小程序批量操作-提交参数 </p>
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
@ApiModel("系统管理-商户管理-小程序批量操作-提交参数")
public class SysMerchantAppOperationDTO extends BaseDTO implements HandleParam, CheckParam {

    //    @NotEmpty(message = "主键id不能为空")
    @ApiModelProperty(value = "主键id", example = "[1]")
    private List<Integer> idList;

    /**
     * {@link com.zhengqing.system.enums.SysMerchantAppStatusEnum}
     */
    @NotNull(message = "小程序状态类型不能为空")
    @ApiModelProperty("小程序状态")
    private Integer appStatus;

    @ApiModelProperty("提交代码描述")
    private String uploadCodeDesc;

    @ApiModelProperty("模板id")
    private String templateId;

    @Override
    public void handleParam() {

    }

    @Override
    public void checkParam() throws ParameterException {
        if (SysMerchantAppStatusEnum.提交代码.getStatus().equals(this.appStatus)) {
            Assert.notBlank(this.uploadCodeDesc, "提交代码描述不能为空！");
            Assert.notBlank(this.templateId, "模板id不能为空！");
        }
    }
}
