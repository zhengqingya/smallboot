package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("数据字典")
@TableName("t_sys_dict")
public class SysDict extends BaseEntity<SysDict> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "字典类型id")
    private Integer dictTypeId;

    @ApiModelProperty(value = "字典类型编码")
    private String code;

    @ApiModelProperty(value = "字典名")
    private String name;

    @ApiModelProperty(value = "字典值")
    private String value;

    @ApiModelProperty(value = "状态(0->停用 1->正常)")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

}
