package com.zhengqing.cms.enums;

import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 职位申请状态类型枚举 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 9:50
 */
@Getter
@AllArgsConstructor
public enum CmsJobApplyStatusEnum {

    待录取(1, "待录取"),
    录取(2, "录取"),
    拒绝(3, "拒绝"),
    已撤销(4, "已撤销");

    private final Integer status;
    private final String desc;

    private static final List<CmsJobApplyStatusEnum> LIST = Arrays.asList(CmsJobApplyStatusEnum.values());

    public static CmsJobApplyStatusEnum getEnum(Integer status) {
        for (CmsJobApplyStatusEnum itemEnum : LIST) {
            if (itemEnum.getStatus().equals(status)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的工作申请状态类型！");
    }

}
