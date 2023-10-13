package com.zhengqing.cms.model.dto;

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
import java.util.List;

/**
 * <p> 内容管理-招聘岗位-保存-提交参数 </p>
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
@ApiModel("内容管理-招聘岗位-保存-提交参数")
public class CmsJobSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    private Integer id;

    @NotNull(message = "请选择商户！")
    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;

    @ApiModelProperty("部门id")
    private Integer deptId;

    @NotBlank(message = "名称不能为空！")
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("岗位id")
    private Integer postId;

    @NotNull(message = "分类不能为空！")
    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("状态(0:停用 1:正常)")
    private Integer status;

    @ApiModelProperty("联系人")
    private String contact;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("福利标签")
    private List<Integer> tagList;

    @ApiModelProperty("简介")
    private String intro;

    @NotNull(message = "招聘人数不能为空！")
    @ApiModelProperty("招聘人数")
    private Integer userNum;

    @ApiModelProperty("最低工资")
    private Integer wageStart;

    @ApiModelProperty("最高工资")
    private Integer wageEnd;


}
