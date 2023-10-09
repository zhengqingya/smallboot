package com.zhengqing.system.model.vo;

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
 * <p>系统管理-部门-树-响应参数</p>
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
@ApiModel("系统管理-部门-树-响应参数")
public class SysDeptTreeVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("父部门ID")
    private Integer parentId;

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

    @ApiModelProperty("子级")
    private List<SysDeptTreeVO> children;

    public void handleData() {

    }

}
