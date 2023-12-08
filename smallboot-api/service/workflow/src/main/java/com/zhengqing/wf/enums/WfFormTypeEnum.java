package com.zhengqing.wf.enums;

import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 表单类型 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/13 20:25
 */
@Getter
@AllArgsConstructor
public enum WfFormTypeEnum {

    PROCESS(1, "流程表单"),
    EXTERNAL(2, "外置表单"),
    INDEPENDENT(3, "节点独立表单");

    private final Integer type;
    private final String desc;

    private static final List<WfFormTypeEnum> LIST = Arrays.asList(WfFormTypeEnum.values());

    public static WfFormTypeEnum getEnum(Integer type) {
        for (WfFormTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的流程表单类型！");
    }

}
