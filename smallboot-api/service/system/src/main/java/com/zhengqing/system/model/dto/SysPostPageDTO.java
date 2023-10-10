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
 * <p> 系统管理-岗位-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 17:49
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-岗位-分页列表-请求参数")
public class SysPostPageDTO extends BaseDTO {

    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @JsonIgnore
    @ApiModelProperty(value = "部门ids")
    private List<Integer> deptIdList;

    @ApiModelProperty("岗位名称")
    private String name;

}
