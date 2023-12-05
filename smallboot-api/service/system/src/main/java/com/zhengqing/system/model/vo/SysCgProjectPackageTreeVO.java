package com.zhengqing.system.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p> 代码生成器-项目包 </p>
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
@ApiModel("代码生成器-项目包")
public class SysCgProjectPackageTreeVO extends BaseVO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("模板内容")
    private String tplContent;

    @ApiModelProperty("名称")
    private List<SysCgProjectPackageTreeVO> childList;

    public void handleData() {

    }

}
