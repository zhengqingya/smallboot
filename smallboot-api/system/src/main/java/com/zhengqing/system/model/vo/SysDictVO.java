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
 * <p>
 * 数据字典表
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:57
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("数据字典表")
public class SysDictVO extends BaseVO {

    @ApiModelProperty(value = "字典类型id(关联`t_sys_dict_type`表`id`字段)")
    private Integer dictTypeId;

    @ApiModelProperty(value = "类型编码")
    private String code;

    @ApiModelProperty(value = "字典ID")
    private Integer id;

    @ApiModelProperty(value = "字典名(展示用)")
    private String name;

    @ApiModelProperty(value = "字典值")
    private String value;

    @ApiModelProperty(value = "展示排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

}
