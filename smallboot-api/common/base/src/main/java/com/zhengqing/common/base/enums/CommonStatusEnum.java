package com.zhengqing.common.base.enums;

import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 通用状态枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/12 0:01
 */
@Getter
@AllArgsConstructor
public enum CommonStatusEnum {

    /**
     * 开启
     */
    ENABLE(1, "开启"),
    /**
     * 关闭
     */
    DISABLE(0, "关闭");

    /**
     * 状态
     */
    private final Integer status;
    /**
     * 描述
     */
    private final String desc;

    private static final List<CommonStatusEnum> LIST = Arrays.asList(CommonStatusEnum.values());

    /**
     * 根据指定状态查找相应枚举类
     */
    public static CommonStatusEnum getEnum(Integer status) {
        for (CommonStatusEnum itemEnum : LIST) {
            if (itemEnum.getStatus().equals(status)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定状态枚举数据！");
    }

}
