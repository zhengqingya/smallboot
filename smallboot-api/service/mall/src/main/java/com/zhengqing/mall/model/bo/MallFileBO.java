package com.zhengqing.mall.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import com.zhengqing.common.core.custom.fileprefix.FilePrefix;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * <p> 商城-文件-数据 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/17 10:12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-文件-数据")
public class MallFileBO extends BaseBO {

    @NotBlank(message = "文件名不能为空!")
    @ApiModelProperty(value = "文件名", required = true, example = "test.jpg")
    private String name;

    @FilePrefix
    @NotBlank(message = "文件url不能为空!")
    @ApiModelProperty(value = "文件url", required = true, example = "https://www.zhengqingya.com/test.png")
    private String url;

}
