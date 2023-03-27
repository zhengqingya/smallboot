package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.common.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.common.model.vo.PmsSpuTypeVO;
import com.zhengqing.mall.entity.PmsSpu;
import com.zhengqing.mall.web.model.dto.*;
import com.zhengqing.mall.web.model.vo.WebPmsSpuDetailVO;
import com.zhengqing.mall.web.model.vo.WebPmsSpuPageVO;

import java.util.List;
import java.util.Map;

/**
 * <p>  商城-商品 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
public interface WebPmsSpuService extends PmsSpuService<PmsSpu> {

    /**
     * 获取tab条件
     *
     * @param params 查询参数
     * @return tab条件
     * @author zhengqingya
     * @date 2021/8/26 15:45
     */
    List<MallTabConditionListVO> getTabCondition(WebPmsSpuPageDTO params);

    /**
     * 列表分页
     *
     * @param params: 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 15:33
     */
    IPage<WebPmsSpuPageVO> page(WebPmsSpuPageDTO params);

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
    WebPmsSpuDetailVO detail(String id);

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
