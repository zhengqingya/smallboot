package com.zhengqing.common.base.model.bo;

import com.zhengqing.common.base.enums.AuthSourceEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    @ApiModelProperty("认证来源")
    private AuthSourceEnum authSourceEnum;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("角色ids")
    private List<Integer> roleIdList;

    @ApiModelProperty("角色编码")
    private List<String> roleCodeList;

    @ApiModelProperty("当前角色以及下级ids")
    private List<Integer> allRoleIdList;

    @ApiModelProperty("小程序openid")
    private String openid;

    @ApiModelProperty(value = "部门ID")
    private Integer deptId;

    @ApiModelProperty(value = "数据权限")
    private List<ScopeDataBO> scopeDataList;

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
