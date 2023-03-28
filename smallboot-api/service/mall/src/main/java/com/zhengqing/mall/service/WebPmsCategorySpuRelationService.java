package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.PmsCategorySpuRelation;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.WebPmsCategorySpuRelationListVO;
import com.zhengqing.mall.model.vo.WebPmsCategorySpuRelationPageVO;

import java.util.List;

/**
 * <p>  商城-分类关联商品 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
public interface WebPmsCategorySpuRelationService extends PmsCategorySpuRelationService<PmsCategorySpuRelation> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 列表
     * @author zhengqingya
     * @date 2022/3/2 10:19
     */
    IPage<WebPmsCategorySpuRelationPageVO> page(WebPmsCategorySpuRelationPageDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 16:04
     */
    List<WebPmsCategorySpuRelationListVO> list(WebPmsCategorySpuRelationListDTO params);

    /**
     * 新增
     *
     * @param params 保存参数
     * @return 主键id
     * @author zhengqingya
     * @date 2022/02/10 16:04
     */
    String addData(WebPmsCategorySpuRelationSaveDTO params);

    /**
     * 批量删除数据
     *
     * @param idList 主键ds
     * @return void
     * @author zhengqingya
     * @date 2022/02/10 16:04
     */
    void deleteBatch(List<String> idList);

    /**
     * 根据分类ids删除关联数据
     *
     * @param categoryIdList 分类ids
     * @return void
     * @author zhengqingya
     * @date 2022/02/10 16:04
     */
    void deleteDataByCategoryIds(List<String> categoryIdList);

    /**
     * 根据商品ids删除关联数据
     *
     * @param spuIdList 商品ids
     * @return void
     * @author zhengqingya
     * @date 2022/02/10 16:04
     */
    void deleteDataBySpuId(List<String> spuIdList);

    /**
     * 批量更新显示状态
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchShow(WebPmsCategorySpuRelationEditShowDTO params);

    /**
     * 批量更新上下架状态
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchPut(WebPmsCategorySpuRelationEditPutDTO params);

    /**
     * 根据商品ids批量更新显示状态
     *
     * @param spuIdList 商品ids
     * @param isShow    是否显示：false->隐藏；true->显示
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchShowBySpuIds(List<String> spuIdList, boolean isShow);

    /**
     * 根据商品ids批量更新上下架状态
     *
     * @param spuIdList 商品ids
     * @param isPut     是否上架：false->下架；true->上架
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchPutBySpuIds(List<String> spuIdList, boolean isPut);

    /**
     * 批量排序
     *
     * @param list 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/9/15 14:16
     */
    void updateBatchSort(List<WebPmsCategorySpuRelationEditSortDTO> list);

}
