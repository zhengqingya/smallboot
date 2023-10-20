package com.zhengqing.ums.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.ums.enums.MiniTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> 绑定手机号 </p>
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
public class UmsUserBindPhoneDTO extends BaseDTO {

    /**
     * {@link MiniTypeEnum}
     */
    @NotNull
    @ApiModelProperty("小程序类型")
    private Integer type;

    @NotBlank
    @ApiModelProperty("包括敏感数据在内的完整用户信息的加密数据")
    private String encryptedData;

    @NotBlank
    @ApiModelProperty("加密算法的初始向量")
    private String iv;

    @ApiModelProperty("敏感数据对应的开放数据 id，上云的小程序才会返回")
    private String cloudId;

}