package com.zhengqing.system.model.bo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 菜单权限树
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:54
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuTree {

    // ==================== ↓↓↓↓↓↓ 菜单基础信息 ↓↓↓↓↓↓ ====================

    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("父ID")
    private Integer parentId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("访问路径")
    private String path;

    @ApiModelProperty(value = "按钮权限标识")
    private String btnPerm;

    @ApiModelProperty("显示顺序")
    private Integer sort;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("重定向url")
    private String redirect;

    @ApiModelProperty("是否显示(1:显示 0:隐藏)")
    private Boolean isShow;

    @ApiModelProperty("是否显示面包屑(1:显示 0:隐藏)")
    private Boolean isShowBreadcrumb;

    @ApiModelProperty("下级菜单")
    private List<SysMenuTree> children;

    @ApiModelProperty("路由元信息(前端用于菜单显示)")
    private Meta meta;

    /**
     * {@link com.zhengqing.system.enums.SysMenuTypeEnum}
     */
    @ApiModelProperty(value = "类型")
    private Integer type;

    // ==================== ↓↓↓↓↓↓ 权限信息 ↓↓↓↓↓↓ ====================

    @ApiModelProperty("是否具有菜单权限")
    private Boolean isHasPerm;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {
        @ApiModelProperty("是否显示(1:显示 0:隐藏)")
        private Boolean isShow;

        @ApiModelProperty("显示顺序")
        private Integer sort;

        @ApiModelProperty("标题")
        private String title;

        @ApiModelProperty("图标")
        private String icon;

        @ApiModelProperty("是否显示面包屑(1:显示 0:隐藏)")
        private Boolean isShowBreadcrumb;
    }

    public void handleData() {
        this.children = CollectionUtils.isEmpty(this.children) ? Lists.newArrayList() : this.children;

        this.meta = Meta.builder()
                .isShow(this.isShow)
                .sort(this.sort)
                .title(this.name)
                .icon(this.icon)
                .isShowBreadcrumb(this.isShowBreadcrumb)
                .build();
    }

}
