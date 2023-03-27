package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.common.model.bo.PmsSkuStockBO;
import com.zhengqing.mall.common.model.dto.PmsSkuDTO;
import com.zhengqing.mall.common.model.vo.PmsSkuVO;
import com.zhengqing.mall.common.model.vo.PmsSpuDetailVO;
import com.zhengqing.mall.entity.PmsSpu;

import java.util.List;
import java.util.Map;

/**
 * <p>  商城-商品 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
public interface PmsSpuService<T> extends IService<PmsSpu> {

    /**
     * 查询商品信息
     *
     * @param id 商品ID
     * @return 商品信息
     * @author zhengqingya
     * @date 2021/6/23 21:23
     */
    PmsSpu getSpu(String id);

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
     * 处理商品关联的服务和说明信息
     *
     * @param spuDetail 商品详情
     * @return void
     * @author zhengqingya
     * @date 2021/12/22 17:12
     */
    void handleSpuData(PmsSpuDetailVO spuDetail);

    /**
     * sku-列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 15:33
     */
    List<PmsSkuVO> listBySku(PmsSkuDTO params);

    /**
     * sku-map
     *
     * @param params 查询参数
     * @return 商品sku-id -> 商品信息
     * @author zhengqingya
     * @date 2021/08/17 15:33
     */
    Map<String, PmsSkuVO> mapBySku(PmsSkuDTO params);

    /**
     * 查询sku
     *
     * @param skuId sku-id
     * @return 商品sku信息
     * @author zhengqingya
     * @date 2021/12/22 11:06
     */
    PmsSkuVO sku(String skuId);

}
