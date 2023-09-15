package com.zhengqing.system.model.vo;


import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>系统管理-系统配置-分页列表-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysConfigPageVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("属性key")
    private String key;

    @ApiModelProperty("属性value")
    private String value;

    @ApiModelProperty("备注")
    private String remark;


}
