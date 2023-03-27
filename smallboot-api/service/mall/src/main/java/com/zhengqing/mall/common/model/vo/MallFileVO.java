package com.zhengqing.mall.common.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 商城-文件-数据 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 10:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商城-文件-数据")
public class MallFileVO {

    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("文件url")
    private String url;

    @ApiModelProperty("文件类型")
    private String type;

}
