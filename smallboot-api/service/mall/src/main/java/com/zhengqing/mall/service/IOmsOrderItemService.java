package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.OmsOrderItem;
import com.zhengqing.mall.model.dto.OmsOrderItemDTO;
import com.zhengqing.mall.model.enums.OmsOrderItemStatusEnum;
import com.zhengqing.mall.model.enums.OmsOrderStatusEnum;
import com.zhengqing.mall.model.vo.OmsOrderItemVO;

import java.util.List;
import java.util.Map;

/**
 * <p>  商城-订单详情 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
public interface IOmsOrderItemService extends IService<OmsOrderItem> {

    /**
     * 根据订单号查询关联订单详情ids
     *
     * @param orderNo 订单号
     * @return 关联订单详情ids
     * @author zhengqingya
     * @date 2021/8/30 10:29 下午
     */
    List<String> orderItemListByOrderNo(String orderNo);

    /**
     * 根据订单号查询关联商品信息
     *
     * @param orderNo 订单号
     * @return 关联商品信息
     * @author zhengqingya
     * @date 2021/8/30 10:29 下午
     */
    List<OmsOrderItemVO> listByOrderNo(String orderNo);

    /**
     * 根据条件查询关联商品信息
     *
     * @param params 查询条件
     * @return 商品信息
     * @author zhengqingya
     * @date 2021/8/30 10:29 下午
     */
    List<OmsOrderItemVO> listInfo(OmsOrderItemDTO params);

    /**
     * 根据订单号查询关联商品信息
     *
     * @param params 查询条件
     * @return 订单 -> 关联商品信息
     * @author zhengqingya
     * @date 2021/10/18 14:49
     */
    Map<String, List<OmsOrderItemVO>> mapInfo(OmsOrderItemDTO params);

    /**
     * 根据ids查询关联信息
     *
     * @param orderItemIdList 订单商品ids
     * @return 订单商品id -> 该商品信息
     * @author zhengqingya
     * @date 2021/10/20 10:19
     */
    Map<String, OmsOrderItemVO> mapSkuBaseInfo(List<String> orderItemIdList);

    /**
     * 详情
     *
     * @param orderItemId 主键ID
     * @return 详情
     * @author zhengqingya
     * @date 2021/08/30 13:55
     */
    OmsOrderItem detail(String orderItemId);

    /**
     * 删除数据
     *
     * @param orderItemId 主键ID
     * @return void
     * @author zhengqingya
     * @date 2021/08/30 13:55
     */
    void deleteData(String orderItemId);

    /**
     * 批量新增数据
     *
     * @param omsOrderItemList 订单详情
     * @return void
     * @author zhengqingya
     * @date 2021/10/14 18:09
     */
    void addBatchData(List<OmsOrderItem> omsOrderItemList);

    /**
     * 更新商品状态
     *
     * @param orderItemIdList        商品ids
     * @param omsOrderItemStatusEnum 状态枚举类
     * @return void
     * @author zhengqingya
     * @date 2021/10/20 9:19
     */
    void updateBatchStatus(List<String> orderItemIdList, OmsOrderItemStatusEnum omsOrderItemStatusEnum);

    /**
     * 更新商品状态 -- 发放优惠券
     *
     * @param orderItemIdList 商品ids
     * @return void
     * @author zhengqingya
     * @date 2021/10/20 9:19
     */
    void updateBatchStatusForSendCoupon(List<String> orderItemIdList);

    /**
     * 更新该订单关联的所有商品状态
     *
     * @param orderNo            订单号
     * @param omsOrderStatusEnum 状态枚举类
     * @return void
     * @author zhengqingya
     * @date 2021/10/20 9:19
     */
    void updateBatchStatusByOrderNo(String orderNo, OmsOrderItemStatusEnum omsOrderStatusEnum);

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
