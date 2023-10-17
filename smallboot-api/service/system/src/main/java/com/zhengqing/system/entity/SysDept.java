package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import com.zhengqing.system.enums.SysAppStatusEnum;
import com.zhengqing.system.enums.SysAppTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p>  系统管理-部门 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 18:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_dept")
@ApiModel("系统管理-部门")
public class SysDept extends BaseEntity<SysDept> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("父ID")
    private Integer parentId;

    @ApiModelProperty("名称")
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

    @ApiModelProperty("过期时间")
    private Date expireTime;

    @ApiModelProperty("最大员工数")
    private Integer userNum;

    @ApiModelProperty("最大职位发布数")
    private Integer jobNum;

    /**
     * {@link SysAppTypeEnum}
     */
    @ApiModelProperty("小程序类型")
    private Integer appType;

    @ApiModelProperty("AppID")
    private String appId;

    @ApiModelProperty("AppSecret")
    private String appSecret;

    /**
     * {@link SysAppStatusEnum}
     */
    @ApiModelProperty("小程序状态")
    private Integer appStatus;

    @ApiModelProperty("小程序首页标题")
    private String appIndexTitle;

}
