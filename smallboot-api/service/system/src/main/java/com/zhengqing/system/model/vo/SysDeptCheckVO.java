package com.zhengqing.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.system.enums.SysAppTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p>系统管理-部门-校验数据</p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 18:10
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SysDeptCheckVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    /**
     * {@link SysAppTypeEnum}
     */
    @ApiModelProperty("小程序类型")
    private Integer appType;

    @ApiModelProperty("状态(0:停用 1:正常)")
    private Integer status;

    @ApiModelProperty("过期时间")
    private Date expireTime;

    @ApiModelProperty("最大员工数")
    private Integer userNum;

    @JsonIgnore
    @ApiModelProperty("AppSecret")
    private String appSecret;

}
