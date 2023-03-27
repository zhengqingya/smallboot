package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.OmsOrderAfterSaleItem;

import java.util.List;

/**
 * <p>  商城-售后详情表 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
public interface OmsOrderAfterSaleItemService<T> extends IService<OmsOrderAfterSaleItem> {

    /**
     * 批量保存数据
     *
     * @param omsOrderAfterSaleItemList 详情数据
     * @return void
     * @author zhengqingya
     * @date 2021/10/20 17:17
     */
    void addOrUpdateBatchData(List<OmsOrderAfterSaleItem> omsOrderAfterSaleItemList);

}
