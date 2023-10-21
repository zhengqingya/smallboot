package com.zhengqing.system.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>
 * 系统管理-用户基础信息表查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 10:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-用户基础信息表查询参数")
public class SysUserListDTO extends BaseDTO {

    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @JsonIgnore
    @ApiModelProperty(value = "部门ids")
    private List<Integer> deptIdList;

    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "true:只要绑定小程序用户的数据，false:只要未绑定的数据")
    private Boolean isBindMiniUser;

}
