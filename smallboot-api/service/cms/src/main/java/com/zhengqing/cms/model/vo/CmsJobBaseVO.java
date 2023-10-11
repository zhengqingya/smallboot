package com.zhengqing.cms.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * <p>内容管理-招聘岗位-base-响应参数</p>
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
@ApiModel("内容管理-招聘岗位-base-响应参数")
public class CmsJobBaseVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("部门id")
    private Integer deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("岗位id")
    private Integer postId;

    @ApiModelProperty("岗位名称")
    private String postName;

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("分类名")
    private String categoryName;

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

    @ApiModelProperty("福利标签")
    private List<String> tagNameList;

    @ApiModelProperty("简介")
    private String intro;

    @ApiModelProperty("招聘人数")
    private Integer userNum;

    @ApiModelProperty("最低工资")
    private Integer wageStart;

    @ApiModelProperty("最高工资")
    private Integer wageEnd;

    @ApiModelProperty("创建时间")
    private Date createTime;


    public void handleData() {

    }

}
