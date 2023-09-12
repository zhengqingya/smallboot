package com.zhengqing.common.db.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 模板文件信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/15 18:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("模板文件信息")
public class GeneratorCodeTemplateFileBO {

    @ApiModelProperty(value = "模板文件名")
    private String fileName;

    @ApiModelProperty(value = "待生成文件后缀")
    private String generateFileSuffix;

    @ApiModelProperty(value = "模板内容")
    private String templateContent;

    @ApiModelProperty(value = "模板关联包")
    private String templateRePackage;

}
