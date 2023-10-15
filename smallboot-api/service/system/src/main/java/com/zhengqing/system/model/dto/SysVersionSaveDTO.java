package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.enums.SysAppStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p> 系统管理-版本记录-保存-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/15 14:58
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-版本记录-保存-提交参数")
public class SysVersionSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @ApiModelProperty("版本号")
    private String version;

    @ApiModelProperty("名称")
    private String name;

    /**
     * {@link SysAppStatusEnum}
     */
    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("小程序审核结果")
    private String appAuditResultList;

    @ApiModelProperty("备注")
    private String remark;


    /**
     * {@link com.zhengqing.system.enums.SysVersionTypeEnum}
     */
    @ApiModelProperty("类型")
    private Integer type;


}
