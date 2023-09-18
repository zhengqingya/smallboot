package com.zhengqing.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.mall.entity.OmsOrderShippingItem;
import com.zhengqing.mall.mapper.OmsOrderShippingItemMapper;
import com.zhengqing.mall.service.IOmsOrderShippingItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p> 商城-订单配送详情表 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:56
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OmsOrderShippingItemServiceImpl extends ServiceImpl<OmsOrderShippingItemMapper, OmsOrderShippingItem> implements IOmsOrderShippingItemService {

    private final OmsOrderShippingItemMapper omsOrderShippingItemMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateBatchData(List<OmsOrderShippingItem> omsOrderShippingItemList) {
        if (CollectionUtils.isEmpty(omsOrderShippingItemList)) {
            return;
        }
        omsOrderShippingItemList.forEach(item -> {
            if (StringUtils.isBlank(item.getId())) {
                item.setId(IdGeneratorUtil.nextStrId());
            }
        });
        super.saveOrUpdateBatch(omsOrderShippingItemList);
    }

    @Override
    public boolean checkSend(String orderNo, List<String> orderItemIdList) {
        OmsOrderShippingItem omsOrderShippingItem = this.omsOrderShippingItemMapper.selectOne(new LambdaQueryWrapper<OmsOrderShippingItem>()
                .eq(OmsOrderShippingItem::getOrderNo, orderNo)
                .in(OmsOrderShippingItem::getOrderItemId, orderItemIdList)
                .last(MybatisConstant.LIMIT_ONE)
        );
        return omsOrderShippingItem != null;
    }

    @Override
    public List<String> listForOrderItemId(String orderNo, String shippingId) {
        return this.omsOrderShippingItemMapper.selectListForOrderItemId(orderNo, shippingId);
    }

}
