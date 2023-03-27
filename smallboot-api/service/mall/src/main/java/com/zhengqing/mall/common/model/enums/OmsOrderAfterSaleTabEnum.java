package com.zhengqing.mall.common.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单-售后-tab-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum OmsOrderAfterSaleTabEnum {

    /**
     * 全部
     */
    ALL((byte) -1, 1, "全部"),
    /**
     * 待处理
     * 1、申请退款，未处理退款的售后；
     * 2、申请退货退款，未处理申请的售后；
     * 3、申请退货退款，未处理退款的售后；
     */
    UN_HANDLE((byte) 1, 2, "待处理"),
    /**
     * 处理中
     * 1.退款腾讯接口未返回信息，金额未实际到账的状态；
     * 2.退货退款中，等待用户填写退款信息的状态；
     * 3.此状态是为了描述，售后流程未结束，B端无法对此售后进行处理的售后集合
     */
    HANDLE_ING((byte) 2, 3, "处理中"),
    /**
     * 已关闭
     * 1、申请退款：因用户撤销而关闭的售后、因商家超过系统设定处理的时间未处理申请的售后；
     * 2、申请退货退款：因用户撤销而关闭的售后、因商家超过系统设定处理的时间未处理申请的售后、商家同意退货退款买家未填写订单号，超过了系统设定的时间而关闭的售后
     */
    CLOSE((byte) 3, 4, "已关闭"),
    /**
     * 已成功
     * 1、申请退款：商家同意退款的售后；
     * 2、申请退货退款：商家同意了申请且同意了退款的售后；
     */
    OK((byte) 4, 5, "已成功"),
    /**
     * 已失败
     * 1、申请退款：商家拒绝了申请退款的售后；
     * 2、申请退货退款：商家拒绝了申请/拒绝了退款的售后
     */
    FAIL((byte) 5, 6, "已失败");


    /**
     * 值
     */
    private final Byte value;
    /**
     * tab条件查询时的排序值
     */
    private final Integer tabSort;
    /**
     * 描述
     */
    private final String name;

    public static final List<OmsOrderAfterSaleTabEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderAfterSaleTabEnum.values()));
    }

    /**
     * 根据指定tab查找相应枚举
     */
    public static OmsOrderAfterSaleTabEnum getEnum(Byte value) {
        for (OmsOrderAfterSaleTabEnum itemEnum : LIST) {
            if (itemEnum.getValue().equals(value)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定售后tab！");
    }

}
