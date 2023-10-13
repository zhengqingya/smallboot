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

/**
 * <p>内容管理-招聘岗位分类-分页列表-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:09
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("内容管理-招聘岗位分类-分页列表-响应参数")
public class CmsJobCategoryPageVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;

    @ApiModelProperty(value = "商户名")
    private String merchantName;

    @ApiModelProperty("部门id")
    private Integer deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("状态(0:停用 1:正常)")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;


    public void handleData() {

    }

}
