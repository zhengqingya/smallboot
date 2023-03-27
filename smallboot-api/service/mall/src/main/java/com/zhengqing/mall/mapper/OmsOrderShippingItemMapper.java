package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.mall.entity.OmsOrderShippingItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 商城-订单配送详情表 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:56
 */
public interface OmsOrderShippingItemMapper extends BaseMapper<OmsOrderShippingItem> {

    /**
     * 查询该配送关联的商品ids
     *
     * @param orderNo    订单号
     * @param shippingId 配送id
     * @return 商品ids
     * @author zhengqingya
     * @date 2021/10/22 9:22
     */
    List<String> selectListForOrderItemId(@Param("orderNo") String orderNo, @Param("shippingId") String shippingId);

}
