package com.zhengqing.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.common.core.custom.parameter.HandleParam;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.system.model.bo.SysMenuTree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>
 * 系统管理 - 用户信息
 * </p>
 *
 * @author zhengqingya
 * @description 基本信息+角色+权限...
 * @date 2020/4/15 10:48
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SysUserPermVO extends BaseVO implements HandleParam {

    // ================= ↓↓↓↓↓↓ 基本信息 ↓↓↓↓↓↓ =================

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "账号")
    private String username;

    @JsonIgnore
    @ApiModelProperty(value = "登录密码", hidden = true)
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别")
    private UserSexEnum sexEnum;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    // ================= ↓↓↓↓↓↓ 角色信息 ↓↓↓↓↓↓ =================

    @JsonIgnore
    @ApiModelProperty(value = "角色ID", hidden = true)
    private List<Integer> roleIdList;

    @ApiModelProperty("角色编码")
    private List<String> roleCodeList;

    // ================= ↓↓↓↓↓↓ 权限信息 ↓↓↓↓↓↓ =================

    @ApiModelProperty(value = "可访问的菜单+按钮权限")
    private List<SysMenuTree> permissionTreeList;

    @Override
    public void handleParam() {

    }
    
}
