package com.zhengqing.system.model.vo;

import com.zhengqing.common.core.custom.fileprefix.FilePrefix;
import com.zhengqing.common.core.custom.fileprefix.FilePrefixValid;
import com.zhengqing.common.core.enums.UserSexEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统管理 - 用户信息列表-展示内容
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 10:48
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FilePrefixValid
public class SysUserListVO {

    @ApiModelProperty(value = "主键ID")
    private Integer userId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * {@link com.zhengqing.common.core.enums.UserSexEnum}
     */
    @ApiModelProperty(value = "性别")
    private Byte sex;

    @ApiModelProperty("性别值")
    private String sexName;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @FilePrefix
    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty("角色ids")
    private List<Integer> roleIdList;

    @ApiModelProperty(value = "角色名称")
    private String roleNames;

    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "岗位ids")
    private List<Integer> postIdList;

    @ApiModelProperty(value = "是否固定(false->否 true->是)")
    private Boolean isFixed;

    @ApiModelProperty(value = "是否在线(false->否 true->是)")
    private Boolean isOnline;

    public void handleData() {
        this.sexName = UserSexEnum.getEnum(this.sex).getDesc();
    }

}
