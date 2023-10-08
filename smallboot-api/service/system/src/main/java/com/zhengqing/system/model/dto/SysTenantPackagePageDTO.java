package com.zhengqing.system.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p> 系统管理-租户套餐-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 10:34
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-租户套餐-分页列表-请求参数")
public class SysTenantPackagePageDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("套餐名")
    private String name;

    @ApiModelProperty("状态(1:开启 0:禁用)")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("关联的菜单ids")
    private String menuIdList;

    @ApiModelProperty("创建人")
    private Long createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改人")
    private Long updateBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("是否删除(1->是，0->否)")
    private Boolean isDeleted;

}
