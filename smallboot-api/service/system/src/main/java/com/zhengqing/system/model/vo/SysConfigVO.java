package com.zhengqing.system.model.vo;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.system.enums.SysConfigKeyEnum;
import com.zhengqing.system.enums.SysConfigTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>系统管理-系统配置-列表-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统管理-系统配置-列表-响应参数")
public class SysConfigVO extends BaseVO {

    @JsonIgnore
    @ApiModelProperty(value = "主键ID", hidden = true)
    private String id;

    @ApiModelProperty("属性key")
    private String key;

    @ApiModelProperty("属性value")
    private Object value;

    @ApiModelProperty("备注")
    private String remark;

    /**
     * {@link SysConfigTypeEnum}
     */
    @ApiModelProperty("类型（1:配置 2:属性）")
    private Integer type;

    public void handleData() {
        if (SysConfigKeyEnum.MALL_INDEX_SLIDE_IMG_LIST.getKey().equals(this.key)) {
            this.value = JSONUtil.toList(StrUtil.toString(this.value), JSONObject.class);
        }
    }

}
