package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  系统管理-文件上传记录 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/28 11:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_file")
@ApiModel("系统管理-文件上传记录")
public class SysFile extends IsDeletedBaseEntity<SysFile> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("文件地址")
    private String url;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件唯一标识（md5）")
    private String md5;

    @ApiModelProperty("文件大小（byte）")
    private Long size;

    @ApiModelProperty("服务器环境")
    private String env;
    
}
