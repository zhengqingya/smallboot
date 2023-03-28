package com.zhengqing.mall.service;

import com.zhengqing.mall.entity.PmsSku;
import com.zhengqing.mall.model.dto.WebPmsSpuEditVirtualUseStockDTO;

import java.util.List;

/**
 * <p>  商城-商品规格 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
public interface WebPmsSkuService extends PmsSkuService<PmsSku> {

    /**
     * 列表
     *
     * @param skuIdList 规格ids
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 18:22
     */
    List<PmsSku> listByIdList(List<String> skuIdList);

    /**
     * 删除规格
     *
     * @param skuId 规格id
     * @return void
     * @author zhengqingya
     * @date 2021/8/17 19:49
     */
    void deleteData(String skuId);

    /**
     * 删除规格
     *
     * @param spuIdList 商品ids
     * @return void
     * @author zhengqingya
     * @date 2021/8/17 19:49
     */
    void deleteDataBySpuIdList(List<String> spuIdList);

    /**
     * 批量修改虚拟销量
     *
     * @param list 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/9/15 14:16
     */
    void updateBatchVirtualUseStock(List<WebPmsSpuEditVirtualUseStockDTO> list);

}
