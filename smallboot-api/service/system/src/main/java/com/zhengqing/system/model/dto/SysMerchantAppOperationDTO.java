package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.parameter.HandleParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
public class SysMerchantAppOperationDTO extends BaseDTO implements HandleParam {

    //    @NotEmpty(message = "主键id不能为空")
    @ApiModelProperty(value = "主键id", example = "[1]")
    private List<Integer> idList;

    @ApiModelProperty("是否提审代码")
    private Boolean isAudit;

    @ApiModelProperty("是否发布代码")
    private Boolean isRelease;

    @Override
    public void handleParam() {
        if (this.isAudit == null) {
            this.isAudit = false;
        }
        if (this.isRelease == null) {
            this.isRelease = false;
        }
    }
}
