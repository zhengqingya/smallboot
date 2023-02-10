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
 * 数据字典类型表
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
@ApiModel("数据字典类型表")
@TableName("t_sys_dict_type")
public class SysDictType extends BaseEntity<SysDictType> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "字典类型编码")
    private String code;

    @ApiModelProperty(value = "字典类型名称(展示用)")
    private String name;

    @ApiModelProperty(value = "状态(0->停用 1->正常)")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否固定(false->否 true->是)")
    private Boolean isFixed;

}
