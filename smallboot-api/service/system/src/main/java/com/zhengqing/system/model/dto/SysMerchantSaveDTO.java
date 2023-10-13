package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.CreateGroup;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
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
 * <p> 系统管理-商户管理-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-商户管理-保存-提交参数")
public class SysMerchantSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @ApiModelProperty("自定义商户ID")
    private Integer customId;

    @NotBlank(message = "名称不能为空！")
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("状态(0:停用 1:正常)")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    /**
     * {@link com.zhengqing.system.enums.SysMerchantTypeEnum}
     */
    @NotNull(message = "商户类型不能为空！")
    @ApiModelProperty("商户类型")
    private Integer type;

    @NotNull(message = "过期时间不能为空！")
    @ApiModelProperty("过期时间")
    private Date expireTime;

    @NotNull(message = "用户数不能为空！")
    @ApiModelProperty("最大用户数")
    private Integer userNum;

    @NotNull(message = "发布数不能为空！")
    @ApiModelProperty("最大职位发布数")
    private Integer jobNum;

    @ApiModelProperty(value = "账号")
    @NotBlank(groups = {CreateGroup.class}, message = "账号不能为空")
    @Length(max = 100, message = "账号不能超过100个字符")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9\\*]*$", message = "账号限制：最多100字符，包含文字、字母和数字")
    private String username;

    @Length(min = 6, message = "密码最少6位数!")
    @NotBlank(groups = {CreateGroup.class}, message = "密码不能为空！")
    @ApiModelProperty(value = "密码")
    private String password;

}
