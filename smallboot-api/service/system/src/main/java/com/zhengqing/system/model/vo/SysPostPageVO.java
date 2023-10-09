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

/**
 * <p>系统管理-岗位-分页列表-响应参数</p>
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
@ApiModel("系统管理-岗位-分页列表-响应参数")
public class SysPostPageVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("岗位名称")
    private String name;

    @ApiModelProperty("岗位编码")
    private String code;

    @ApiModelProperty("状态(1:正常 0:停用)")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;

    public void handleData() {

    }

}
