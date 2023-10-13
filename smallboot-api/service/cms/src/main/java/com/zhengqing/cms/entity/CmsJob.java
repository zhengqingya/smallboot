package com.zhengqing.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.config.mybatis.handler.ListIntegerTypeHandler;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * <p>  内容管理-招聘岗位 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "cms_job", autoResultMap = true)
@ApiModel("内容管理-招聘岗位")
public class CmsJob extends IsDeletedBaseEntity<CmsJob> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;

    @ApiModelProperty("部门id")
    private Integer deptId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("岗位id")
    private Integer postId;

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
    @TableField(typeHandler = ListIntegerTypeHandler.class)
    private List<Integer> tagList;

    @ApiModelProperty("简介")
    private String intro;

    @ApiModelProperty("招聘人数")
    private Integer userNum;

    @ApiModelProperty("最低工资")
    private Integer wageStart;

    @ApiModelProperty("最高工资")
    private Integer wageEnd;

}
