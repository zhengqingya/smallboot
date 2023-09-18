package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.PmsSpu;
import com.zhengqing.mall.model.bo.PmsSkuStockBO;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.*;

import java.util.List;
import java.util.Map;

/**
 * <p>  商城-商品 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
public interface IPmsSpuService extends IService<PmsSpu> {

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
    void handleSpuData(PmsSpuBaseVO spuDetail);

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

    /**
     * 预售提醒 -- 存储需要通知的用户信息
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/12/31 10:31
     */
    void presellRemind(MiniPmsSpuPresellRemindDTO params);

    /**
     * 预售提醒 -- 给用户发送消息通知
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/12/31 10:31
     */
    void presellRemindToSendUser(PmsSpuPresellDTO params);

    /**
     * 获取tab条件
     *
     * @param params 查询参数
     * @return tab条件
     * @author zhengqingya
     * @date 2021/8/26 15:45
     */
    List<MallTabConditionListVO> getTabCondition(PmsSpuPageDTO params);

    /**
     * 列表分页
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 15:33
     */
    IPage<PmsSpuBaseVO> page(PmsSpuPageDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 15:33
     */
    List<WebPmsSpuListVO> list(WebPmsSpuListDTO params);

    /**
     * 查询商品类型
     *
     * @param idList 商品ids
     * @return 商品id -> 商品类型
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    Map<String, PmsSpuTypeVO> mapSpuType(List<String> idList);

    /**
     * 详情
     *
     * @param id 商品id
     * @return 商品详情
     * @author zhengqingya
     * @date 2021/8/20 9:18
     */
    PmsSpuBaseVO detail(String id);

    /**
     * 新增或更新
     *
     * @param params: 保存参数
     * @return 主键id
     * @author zhengqingya
     * @date 2021/08/17 15:33
     */
    String addOrUpdateData(WebPmsSpuSaveDTO params);

    /**
     * 批量更新上下架状态
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchPut(WebPmsSpuEditPutDTO params);

    /**
     * 批量更新显示状态
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchShow(WebPmsSpuEditShowDTO params);

    /**
     * 批量更新预售状态
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchPresell(WebPmsSpuEditPresellDTO params);

    /**
     * 批量删除 -- 业务处理
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:05
     */
    void deleteBatchForBusiness(WebPmsSpuDeleteDTO params);

    /**
     * 批量删除
     *
     * @param spuIdList 商品ids
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:05
     */
    void deleteBatch(List<String> spuIdList);

    /**
     * 批量排序
     *
     * @param list 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/9/15 14:16
     */
    void updateBatchSort(List<WebPmsSpuEditSortListDTO> list);

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
