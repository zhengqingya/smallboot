package com.zhengqing.mall.service.impl;

import com.zhengqing.mall.entity.OmsOrderAfterSaleItem;
import com.zhengqing.mall.mapper.OmsOrderAfterSaleItemMapper;
import com.zhengqing.mall.service.MiniOmsOrderAfterSaleItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> 商城-售后详情 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
@Slf4j
@Service
public class MiniOmsOrderAfterSaleItemServiceImpl extends OmsOrderAfterSaleItemServiceImpl<OmsOrderAfterSaleItemMapper, OmsOrderAfterSaleItem> implements MiniOmsOrderAfterSaleItemService {

    @Resource
    private OmsOrderAfterSaleItemMapper omsOrderAfterSaleItemMapper;


}
