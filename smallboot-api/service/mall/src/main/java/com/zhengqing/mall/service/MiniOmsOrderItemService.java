package com.zhengqing.mall.service;

import com.zhengqing.mall.common.model.enums.OmsOrderStatusEnum;
import com.zhengqing.mall.entity.OmsOrderItem;

import java.util.List;
import java.util.Map;

/**
 * <p>  商城-订单详情 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
public interface MiniOmsOrderItemService extends OmsOrderItemService<OmsOrderItem> {

    /**
     * 获取指定用户之前购买的商品数
     *
     * @param userId    用户id
     * @param skuIdList sku信息
     * @return sku -> 历史购买数
     * @author zhengqingya
     * @date 2021/10/14 15:06
     */
    Map<String, Integer> mapSkuLimit(Long userId, List<String> skuIdList);

    /**
     * 根据ids查询相应状态
     *
     * @param orderItemIdList 订单商品ids
     * @return 订单商品id -> 该商品状态
     * @author zhengqingya
     * @date 2021/10/20 10:19
     */
    Map<String, OmsOrderStatusEnum> mapStatusByIdList(List<String> orderItemIdList);

    /**
     * 更新商品是否已经评价值
     *
     * @param orderItemIdList 商品ids
     * @param isRate          是否已经评价(false->否 true->是)
     * @return void
     * @author zhengqingya
     * @date 2021/10/20 9:19
     */
    void updateBatchIsRate(List<String> orderItemIdList, Boolean isRate);

}
