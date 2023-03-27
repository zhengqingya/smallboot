package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.common.model.bo.PmsSkuBO;
import com.zhengqing.mall.common.model.bo.PmsSkuStockBO;
import com.zhengqing.mall.entity.PmsSku;

import java.util.List;
import java.util.Map;

/**
 * <p>  商城-商品规格 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
public interface PmsSkuService<T> extends IService<PmsSku> {

    /**
     * 查询商品sku信息
     *
     * @param skuId 商品sku-ID
     * @return 商品sku信息
     * @author zhengqingya
     * @date 2021/6/23 21:23
     */
    PmsSku getSku(String skuId);

    /**
     * 批量保存商品规格 -- 纯数据保存
     *
     * @param skuList 保存参数
     * @return void
     * @author zhengqingya
     * @date 2021/08/17 18:22
     */
    void batchInsertOrUpdate(List<PmsSku> skuList);

    /**
     * 更新商品sku库存
     *
     * @param skuList 商品sku
     * @return true->成功 false->失败
     * @author zhengqingya
     * @date 2021/10/14 14:17
     */
    boolean updateSkuStock(List<PmsSkuStockBO> skuList);

    /**
     * 列表
     *
     * @param spuId 商品id
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 18:22
     */
    List<PmsSkuBO> listBySpuId(String spuId);

    /**
     * 商品关联规格信息
     *
     * @param spuIdList 商品ids
     * @return 商品id -> 商品规格信息
     * @author zhengqingya
     * @date 2021/08/26 11:44
     */
    Map<String, List<PmsSkuBO>> mapBySpuId(List<String> spuIdList);

    /**
     * 商品关联规格信息
     *
     * @param spuIdList 商品ids
     * @return 商品id -> 商品规格信息
     * @author zhengqingya
     * @date 2021/08/26 11:44
     */
    Map<String, List<PmsSku>> map(List<String> spuIdList);


}
