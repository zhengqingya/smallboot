package com.zhengqing.common.base.model.bo;

import com.zhengqing.common.base.enums.AuthSourceEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 用户token信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 23:16
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserBO extends BaseBO {

    @ApiModelProperty(value = "认证来源")
    private AuthSourceEnum authSourceEnum;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 获取B端用户ID
     */
    public Integer getUserIdForB() {
        return Integer.valueOf(this.userId);
    }

    /**
     * 获取C端用户ID
     */
    public Long getUserIdForC() {
        return Long.valueOf(this.userId);
    }

}
