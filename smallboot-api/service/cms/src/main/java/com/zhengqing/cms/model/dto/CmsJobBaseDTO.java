package com.zhengqing.cms.model.dto;

import com.zhengqing.common.base.model.dto.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p> 内容管理-招聘岗位-base-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:08
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("内容管理-招聘岗位-base-请求参数")
public class CmsJobBaseDTO extends BasePageDTO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("id")
    private List<Integer> idList;

    @ApiModelProperty("部门id")
    private Integer deptId;

    @ApiModelProperty("关键字")
    private String keyword;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("岗位id")
    private Integer postId;

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("状态(0:停用 1:正常)")
    private Integer status;

}
