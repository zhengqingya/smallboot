package com.zhengqing.mall.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单-售后状态-枚举类 </p>
 *
 * @author zhengqingya
 * @description 1->用户申请售后 2->用户撤销申请 3->同意申请 4->拒绝申请 5->申请退款 6->同意退款 7->拒绝退款 8->退款中 9->售后完成 10->已关闭
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum OmsOrderAfterSaleStatusEnum {

    /**
     * 用户申请售后(待处理) -- 仅退货退款使用
     */
    APPLY((byte) 1, "用户申请售后"),
    /**
     * 用户撤销申请
     */
    REVOKE((byte) 2, "用户撤销申请"),
    /**
     * 同意申请 -- 仅退货退款使用
     */
    AGREE_APPLY((byte) 3, "同意申请"),
    /**
     * 拒绝申请 -- 仅退货退款使用
     */
    REJECT_APPLY((byte) 4, "拒绝申请"),
    /**
     * 申请退款
     */
    APPLY_REFUND((byte) 5, "申请退款"),
    /**
     * 同意退款
     */
    AGREE_REFUND((byte) 6, "同意退款"),
    /**
     * 拒绝退款
     */
    REJECT_REFUND((byte) 7, "拒绝退款"),
    /**
     * 退款中
     */
    REFUND_ING((byte) 8, "退款中"),
    /**
     * 售后完成
     */
    FINISH((byte) 9, "售后完成"),
    /**
     * 已关闭
     */
    CLOSE((byte) 10, "已关闭");

    /**
     * 状态
     */
    private final Byte status;
    /**
     * 描述
     */
    private final String desc;

    public static final List<OmsOrderAfterSaleStatusEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderAfterSaleStatusEnum.values()));
    }

    /**
     * 根据指定状态查找相应枚举
     */
    public static OmsOrderAfterSaleStatusEnum getEnum(Byte status) {
        for (OmsOrderAfterSaleStatusEnum itemEnum : LIST) {
            if (itemEnum.getStatus().equals(status)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定售后状态！");
    }

}
