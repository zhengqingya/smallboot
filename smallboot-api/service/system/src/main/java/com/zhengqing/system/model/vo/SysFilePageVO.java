package com.zhengqing.system.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.common.core.custom.fileprefix.FilePrefix;
import com.zhengqing.common.core.custom.fileprefix.FilePrefixValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p>系统管理-文件上传记录-分页列表-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/28 11:24
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FilePrefixValid
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-文件上传记录-分页列表-响应参数")
public class SysFilePageVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("文件名")
    private String name;

    @FilePrefix
    @ApiModelProperty("文件地址")
    private String url;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件唯一标识（md5）")
    private String md5;

    @ApiModelProperty("文件大小（byte）")
    private Long size;

    @ApiModelProperty("创建时间")
    private Date createTime;

    public void handleData() {

    }

}
