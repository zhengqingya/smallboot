package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  系统管理-省市区 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/14 11:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_province_city_area")
@ApiModel("系统管理-省市区")
public class SysProvinceCityArea extends BaseEntity<SysProvinceCityArea> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("父ID")
    private Integer parentId;

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

    @ApiModelProperty("是否存在门店（1:是 0:否）")
    private Boolean isShop;

}
