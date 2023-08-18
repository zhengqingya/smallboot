package com.zhengqing.system.model.vo;

import com.zhengqing.common.core.custom.fileprefix.FilePrefix;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 文件-数据 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 10:07
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SysFileVO {

    @ApiModelProperty("文件名")
    private String name;

    @FilePrefix
    @ApiModelProperty("文件url")
    private String url;

    @ApiModelProperty("文件类型")
    private String type;

}
