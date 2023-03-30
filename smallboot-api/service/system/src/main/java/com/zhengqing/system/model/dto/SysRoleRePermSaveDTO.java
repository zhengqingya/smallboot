package com.zhengqing.system.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.ParameterException;
import com.zhengqing.common.core.custom.parameter.ParamCheck;
import com.zhengqing.system.model.bo.SysMenuTree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 保存角色权限信息参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 15:00
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("保存角色权限信息参数")
public class SysRoleRePermSaveDTO implements ParamCheck {

    @NotNull(message = "角色id不能为空！")
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @NotEmpty
    @ApiModelProperty(value = "菜单权限树")
    private List<SysMenuTree> menuTree;

    // 下面是自己需要的字段 -- 隐藏仅给后端处理

    @JsonIgnore
    @ApiModelProperty(value = "有权限的菜单ids", hidden = true)
    private List<Integer> menuIdList;

    @Override
    public void checkParam() throws ParameterException {
        this.menuIdList = Lists.newArrayList();
        this.recurveMenu(this.menuTree);
    }

    private void recurveMenu(List<SysMenuTree> menuList) {
        menuList.forEach(item -> {
            if (item.getIsHasPerm()) {
                this.menuIdList.add(item.getMenuId());
            }
            List<SysMenuTree> children = item.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                this.recurveMenu(children);
            }
        });
    }

}
