package com.zhengqing.common.db.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 表字段信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/15 21:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("表字段信息")
public class GeneratorCodeColumnInfoBO {

    @ApiModelProperty(value = "数据库字段名")
    private String columnNameDb;

    @ApiModelProperty(value = "Java字段名首字母小写")
    private String columnNameJavaLower;

    @ApiModelProperty(value = "Java字段名首字母大写")
    private String columnNameJavaUpper;

    @ApiModelProperty(value = "注释")
    private String columnComment;

    @ApiModelProperty(value = "数据类型")
    private String columnType;

    @ApiModelProperty(value = "java字段数据类型")
    private String columnTypeJava;

    @ApiModelProperty(value = "是否允许空值")
    private Boolean ifNullAble;

    @ApiModelProperty(value = "是否是主键(值为`PRI`时)")
    private Boolean ifPrimaryKey;

    @ApiModelProperty(value = "是否自增长(值为`auto_increment`时)")
    private Boolean ifAutoIncrement;

}
