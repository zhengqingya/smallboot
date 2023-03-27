package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.mall.common.model.dto.OmsOrderItemDTO;
import com.zhengqing.mall.common.model.vo.OmsOrderItemVO;
import com.zhengqing.mall.entity.OmsOrderItem;
import com.zhengqing.mall.mini.model.bo.MiniOmsOrderItemBuyLimitBO;
import com.zhengqing.mall.mini.model.bo.MiniOmsOrderItemStatusBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 商城-订单详情 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
public interface OmsOrderItemMapper extends BaseMapper<OmsOrderItem> {

    /**
     * 根据ids查询相应状态
     *
     * @param orderItemIdList 订单商品ids
     * @return 订单-商品状态
     * @author zhengqingya
     * @date 2021/10/20 10:19
     */
    List<MiniOmsOrderItemStatusBO> selectListStatusByIdList(@Param("orderItemIdList") List<String> orderItemIdList);

    /**
     * 更新商品状态
     *
     * @param orderItemIdList 商品ids
     * @param status          状态
     * @return void
     * @author zhengqingya
     * @date 2021/10/20 9:19
     */
    void updateBatchStatus(@Param("orderItemIdList") List<String> orderItemIdList, @Param("status") Byte status);

    /**
     * 更新商品状态 -- 发放优惠券
     *
     * @param orderItemIdList 商品ids
     * @return void
     * @author zhengqingya
     * @date 2021/10/20 9:19
     */
    void updateBatchStatusForSendCoupon(@Param("orderItemIdList") List<String> orderItemIdList);


    /**
     * 更新商品是否已经评价值
     *
     * @param orderItemIdList 商品ids
     * @param isRate          是否已经评价(false->否 true->是)
     * @return void
     * @author zhengqingya
     * @date 2021/10/20 9:19
     */
    void updateBatchIsRate(@Param("orderItemIdList") List<String> orderItemIdList, @Param("isRate") Boolean isRate);

    /**
     * 更新该订单关联的所有商品状态
     *
     * @param orderNo 订单号
     * @param status  状态
     * @return void
     * @author zhengqingya
     * @date 2021/10/20 9:19
     */
    void updateBatchStatusByOrderNo(@Param("orderNo") String orderNo, @Param("status") Byte status);

    /**
     * 获取指定用户之前购买的商品数
     *
     * @param userId    用户id
     * @param skuIdList sku信息
     * @return sku -> 购买数
     * @author zhengqingya
     * @date 2021/10/14 15:06
     */
    List<MiniOmsOrderItemBuyLimitBO> selectListForSkuLimit(@Param("userId") Long userId, @Param("skuIdList") List<String> skuIdList);

    /**
     * 查询关联商品信息
     *
     * @param filter 查询过滤参数
     * @return 关联商品信息
     * @author zhengqingya
     * @date 2021/10/18 14:49
     */
    List<OmsOrderItemVO> selectItemList(@Param("filter") OmsOrderItemDTO filter);

}
