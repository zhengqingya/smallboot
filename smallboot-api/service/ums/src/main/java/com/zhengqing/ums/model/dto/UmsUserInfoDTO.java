package com.zhengqing.ums.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.enums.UserSexEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * <p> 用户信息 </p>
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
public class UmsUserInfoDTO extends BaseDTO {

    @JsonIgnore
    @ApiModelProperty(value = "主键ID", hidden = true)
    private Long id;

    @NotBlank(message = "昵称不能为空！")
    @ApiModelProperty("昵称")
    private String nickname;

    @NotBlank(message = "头像不能为空！")
    @ApiModelProperty("头像")
    private String avatarUrl;

    @NotBlank(message = "请完善手机号！")
    @Length(min = 11, max = 11, message = "手机号需要11位！")
    @ApiModelProperty("手机号")
    private String phone;

    @NotBlank(message = "请完善年龄！")
    @ApiModelProperty("生日")
    private String birthday;

    /**
     * {@link UserSexEnum}
     */
    @ApiModelProperty("性别")
    private Byte sex;

}