package com.zhengqing.system.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p> 代码生成器-配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/20 16:03
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("代码生成器-配置")
public class SysCgConfigBO extends BaseBO {

    @ApiModelProperty("父包名")
    private String parentPackageName;

    @ApiModelProperty("模块名")
    private String moduleName;

    @ApiModelProperty("表名")
    private String tableName;

    @ApiModelProperty("表字段")
    private List<String> dbColumnList;

    @ApiModelProperty("查询字段")
    private List<String> queryColumnList;

    @ApiModelProperty("项目包")
    private List<ProjectPackage> pgList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectPackage {
        @ApiModelProperty("名称")
        private String name;

        @ApiModelProperty("模板内容")
        private String tplContent;

        @ApiModelProperty("模板文件路径")
        private String tplFilePath;

        @ApiModelProperty("子包")
        private List<ProjectPackage> childList;
    }

}
