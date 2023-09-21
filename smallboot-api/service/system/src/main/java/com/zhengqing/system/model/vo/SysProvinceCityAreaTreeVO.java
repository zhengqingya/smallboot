package com.zhengqing.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>系统管理-省市区-树-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/14 11:38
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-省市区-树-响应参数")
public class SysProvinceCityAreaTreeVO extends BaseVO {

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @JsonIgnore
    @ApiModelProperty(value = "父ID", hidden = true)
    private Integer parentId;

    @JsonIgnore
    @ApiModelProperty(value = "父代码", hidden = true)
    private String parentCode;

    @ApiModelProperty("名称")
    private String name;

    @JsonIgnore
    @ApiModelProperty(value = "行政区代码", hidden = true)
    private String code;

    /**
     * {@link com.zhengqing.system.enums.SysProvinceCityAreaTypeEnum}
     */
    @JsonIgnore
    @ApiModelProperty(value = "类型（1:省 2:市 3:区）", hidden = true)
    private Integer type;

    @ApiModelProperty("子集")
    private List<SysProvinceCityAreaTreeVO> children;

    public void handleData() {

    }

}
