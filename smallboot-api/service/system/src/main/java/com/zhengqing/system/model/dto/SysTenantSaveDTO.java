package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p> 系统管理-租户信息-保存-提交参数 </p>
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
@ApiModel("系统管理-租户信息-保存-提交参数")
public class SysTenantSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @NotBlank(message = "租户名不能为空！")
    @ApiModelProperty("租户名")
    private String name;

    @NotBlank(message = "联系人不能为空！")
    @ApiModelProperty("联系人")
    private String contactName;

    @NotBlank(message = "联系人手机号不能为空！")
    @ApiModelProperty("联系人手机号")
    private String contactPhone;


    @ApiModelProperty("租户状态(0->停用 1->开启)")
    private Integer status;

    @NotNull(message = "过期时间不能为空！")
    @ApiModelProperty("过期时间")
    private Date expireTime;

    @NotNull(message = "账号数量不能为空！")
    @ApiModelProperty("账号数量")
    private Integer accountCount;

    @NotNull(message = "租户套餐不能为空！")
    @ApiModelProperty("租户套餐ID")
    private Integer packageId;

}
