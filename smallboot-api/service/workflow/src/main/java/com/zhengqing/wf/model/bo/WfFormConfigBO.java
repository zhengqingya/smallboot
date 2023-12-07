package com.zhengqing.wf.model.bo;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 工作流-表单配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("工作流-表单配置")
public class WfFormConfigBO extends BaseBO {

    @ApiModelProperty("FcDesigner 生成的`JSON`")
    private JSONArray rule;

    @ApiModelProperty("FcDesigner 生成的`options`")
    private JSONObject option;


}
