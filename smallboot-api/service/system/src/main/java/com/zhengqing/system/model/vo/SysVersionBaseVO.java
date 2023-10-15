package com.zhengqing.system.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>系统管理-版本记录-分页列表-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/15 14:58
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-版本记录-分页列表-响应参数")
public class SysVersionBaseVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("版本号")
    private String version;
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("小程序审核结果")
    private String appAuditResultList;

    @ApiModelProperty("备注")
    private String remark;

    /**
     * {@link com.zhengqing.system.enums.SysVersionTypeEnum}
     */
    @ApiModelProperty("类型")
    private Integer type;


    public void handleData() {

    }

}
