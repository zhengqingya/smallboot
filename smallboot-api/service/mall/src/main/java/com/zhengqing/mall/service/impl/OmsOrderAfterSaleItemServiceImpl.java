package com.zhengqing.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.mall.entity.OmsOrderAfterSaleItem;
import com.zhengqing.mall.mapper.OmsOrderAfterSaleItemMapper;
import com.zhengqing.mall.service.IOmsOrderAfterSaleItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p> 商城-售后详情表 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OmsOrderAfterSaleItemServiceImpl extends ServiceImpl<OmsOrderAfterSaleItemMapper, OmsOrderAfterSaleItem> implements IOmsOrderAfterSaleItemService {

    private final OmsOrderAfterSaleItemMapper omsOrderAfterSaleItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateBatchData(List<OmsOrderAfterSaleItem> omsOrderAfterSaleItemList) {
        if (CollectionUtils.isEmpty(omsOrderAfterSaleItemList)) {
            return;
        }
        omsOrderAfterSaleItemList.forEach(item -> {
            if (StringUtils.isBlank(item.getId())) {
                item.setId(IdGeneratorUtil.nextStrId());
            }
        });
        super.saveOrUpdateBatch(omsOrderAfterSaleItemList);
    }

}
