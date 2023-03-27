package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.OmsOrderShippingItem;

import java.util.List;

/**
 * <p>  商城-订单配送详情表 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:56
 */
public interface OmsOrderShippingItemService<T> extends IService<OmsOrderShippingItem> {

    /**
     * 批量保存配送信息数据
     *
     * @param omsOrderShippingItemList 配送信息数据
     * @return void
     * @author zhengqingya
     * @date 2021/10/21 10:04
     */
    void addOrUpdateBatchData(List<OmsOrderShippingItem> omsOrderShippingItemList);

    /**
     * 判断此批商品中是否包含已发货数据
     *
     * @param orderNo         订单号
     * @param orderItemIdList 订单关联的商品id
     * @return true->是  false->否
     * @author zhengqingya
     * @date 2021/10/21 10:54
     */
    boolean checkSend(String orderNo, List<String> orderItemIdList);

    /**
     * 查询该配送关联的商品ids
     *
     * @param orderNo    订单号
     * @param shippingId 配送ID
     * @return 关联商品ids
     * @author zhengqingya
     * @date 2021/10/22 9:22
     */
    List<String> listForOrderItemId(String orderNo, String shippingId);

}
