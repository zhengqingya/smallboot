package com.zhengqing.system.model.vo;

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

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("父代码")
    private String parentCode;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("行政区代码")
    private String code;

    /**
     * {@link com.zhengqing.system.enums.SysProvinceCityAreaTypeEnum}
     */
    @ApiModelProperty("类型（1:省 2:市 3:区）")
    private Integer type;

    @ApiModelProperty("子集")
    private List<SysProvinceCityAreaTreeVO> children;

    public void handleData() {

    }

}
