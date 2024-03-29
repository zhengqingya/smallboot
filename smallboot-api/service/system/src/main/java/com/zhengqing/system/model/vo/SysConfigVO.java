package com.zhengqing.system.model.vo;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.vo.BaseVO;
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

    @ApiModelProperty("租户ID")
    private Integer tenantId;

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
//        if (SysConfigKeyEnum.MALL_INDEX_SLIDE_IMG_LIST.getKey().equals(this.key)) {
//            this.value = JSONUtil.toList(StrUtil.toString(this.value), JSONObject.class);
//        }

        String valueStr = String.valueOf(this.value);
        if (JSONUtil.isTypeJSONObject(valueStr)) {
            this.value = JSONUtil.toBean(valueStr, JSONObject.class);
        } else if (JSONUtil.isTypeJSONArray(valueStr)) {
            this.value = JSONUtil.toList(valueStr, JSONArray.class);
        }

    }

}
