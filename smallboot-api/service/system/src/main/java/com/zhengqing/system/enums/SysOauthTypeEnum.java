package com.zhengqing.system.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 授权数据类型
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/27 17:51
 */
@Getter
@AllArgsConstructor
public enum SysOauthTypeEnum {

    Gitee(1, "gitee", "Gitee"),
    GiteeBind(1, "giteeBind", "Gitee账号绑定"),
    GitHub(2, "github", "GitHub"),
    GitHubBind(2, "githubBind", "GitHub账号绑定"),
    QQ(3, "qq", "QQ");

    private final Integer oauthTypeValue;
    private final String oauthTypeName;
    private final String desc;

    private static final List<SysOauthTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(SysOauthTypeEnum.values()));
    }

    public static SysOauthTypeEnum getEnum(String oauthTypeName) {
        for (SysOauthTypeEnum itemEnum : LIST) {
            if (itemEnum.getOauthTypeName().equals(oauthTypeName)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的数据类型！");
    }

}
