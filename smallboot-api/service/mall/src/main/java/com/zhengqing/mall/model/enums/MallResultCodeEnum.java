package com.zhengqing.mall.model.enums;

import com.zhengqing.common.base.enums.ApiResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 商城-响应码枚举 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/14 11:50
 */
@Getter
@AllArgsConstructor
public enum MallResultCodeEnum {

    商品失效(ApiResultCodeEnum.FAILURE.getCode(), "部分商品已失效，请重试！"),
    商品限购(ApiResultCodeEnum.FAILURE.getCode(), "部分商品已超过限购数量，请重试！"),
    商品库存不足(ApiResultCodeEnum.FAILURE.getCode(), "部分商品库存不足，请重试！"),
    商品预售之前无法购买(ApiResultCodeEnum.FAILURE.getCode(), "部分商品预售之前无法购买，请重试！"),
    商品运费变更(ApiResultCodeEnum.FAILURE.getCode(), "部分商品运费变更，请重试！"),
    商品价格变更(ApiResultCodeEnum.FAILURE.getCode(), "部分商品价格变更，请重试！"),
    用户服务繁忙(ApiResultCodeEnum.FAILURE.getCode(), "用户服务繁忙，请稍后再试！"),
    用户不存在(ApiResultCodeEnum.FAILURE.getCode(), "用户不存在！"),
    暂只支持微信支付(ApiResultCodeEnum.FAILURE.getCode(), "暂只支持微信支付！"),
    无权限修改收货人地址(ApiResultCodeEnum.FAILURE.getCode(), "您暂无权限修改收货人地址！"),
    无权限取消订单(ApiResultCodeEnum.FAILURE.getCode(), "您暂无权限取消订单！"),
    退款金额不能为空(ApiResultCodeEnum.FAILURE.getCode(), "退款金额不能为空！"),
    物流单号不能为空(ApiResultCodeEnum.FAILURE.getCode(), "物流单号不能为空！"),
    收货地址不能为空(ApiResultCodeEnum.FAILURE.getCode(), "收货地址不能为空！"),
    退款金额不能超过商品金额(ApiResultCodeEnum.FAILURE.getCode(), "退款金额不能超过商品金额！"),
    不满足申请售后条件(ApiResultCodeEnum.FAILURE.getCode(), "您不满足申请售后条件！"),
    部分商品已发货(ApiResultCodeEnum.FAILURE.getCode(), "部分商品之前已发货，不满足发货条件！"),
    暂无此tab条件数据(ApiResultCodeEnum.FAILURE.getCode(), "暂无此tab条件数据，请联系系统管理员!"),
    优惠券已领取(ApiResultCodeEnum.FAILURE.getCode(), "优惠券已领取！"),
    订单状态已变更(ApiResultCodeEnum.FAILURE.getCode(), "订单状态已变更，您暂无权限处理！"),
    售后状态已变更(ApiResultCodeEnum.FAILURE.getCode(), "售后状态已变更，您暂无权限处理！"),
    ;

    private final int code;
    private final String desc;

}
