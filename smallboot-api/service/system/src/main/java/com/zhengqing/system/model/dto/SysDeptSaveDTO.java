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
 * <p> 系统管理-部门-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 18:10
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-部门-保存-提交参数")
public class SysDeptSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @NotNull(message = "父部门ID不能为空")
    @ApiModelProperty("父部门ID")
    private Integer parentId;

    @NotBlank(message = "部门名称不能为空！")
    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("负责人id")
    private Integer leaderUserId;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("状态(0:停用 1:正常)")
    private Integer status;

    @ApiModelProperty("过期时间")
    private Date expireTime;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("最大用户数")
    private Integer userNum;


}
