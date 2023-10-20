package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.CreateGroup;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.bo.SysAppConfigBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @ApiModelProperty("自定义租户ID")
    private Integer customTenantId;

    @NotBlank(message = "租户名不能为空！")
    @ApiModelProperty("租户名")
    private String name;

    @NotBlank(message = "管理员不能为空！")
    @ApiModelProperty("管理员")
    private String adminName;

    @NotBlank(message = "管理员手机号不能为空！")
    @ApiModelProperty("管理员手机号")
    private String adminPhone;

    @ApiModelProperty("租户状态(0->停用 1->开启)")
    private Integer status;

    @NotNull(message = "过期时间不能为空！")
    @ApiModelProperty("过期时间")
    private Date expireTime;

    @NotNull(message = "账号数量不能为空！")
    @ApiModelProperty("账号数量")
    private Integer accountCount;

    @NotNull(message = "最大职位发布数不能为空！")
    @ApiModelProperty("最大职位发布数")
    private Integer jobNum;

    @NotNull(message = "租户套餐不能为空！")
    @ApiModelProperty("租户套餐ID")
    private Integer packageId;

    @ApiModelProperty(value = "账号")
    @NotBlank(groups = {CreateGroup.class}, message = "账号不能为空")
    @Length(max = 100, message = "账号不能超过100个字符")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9\\*]*$", message = "账号限制：最多100字符，包含文字、字母和数字")
    private String username;

    @Length(min = 6, message = "密码最少6位数!")
    @NotBlank(groups = {CreateGroup.class}, message = "密码不能为空！")
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty("小程序配置")
    private SysAppConfigBO appConfigObj;

    @ApiModelProperty("排序")
    private Integer sort;

}
