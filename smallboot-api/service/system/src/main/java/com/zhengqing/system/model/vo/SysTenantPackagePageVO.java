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
 * <p>系统管理-租户套餐-分页列表-响应参数</p>
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
@ApiModel("系统管理-租户套餐-分页列表-响应参数")
public class SysTenantPackagePageVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("套餐名")
    private String name;

    @ApiModelProperty("状态(1:开启 0:禁用)")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("关联的菜单ids")
    private List<Integer> menuIdList;

    @ApiModelProperty("关联的按钮权限ids")
    private List<Integer> permissionIdList;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public void handleData() {

    }

}
