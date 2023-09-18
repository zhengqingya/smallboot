package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.PmsCategorySpuRelation;
import com.zhengqing.mall.model.dto.PmsCategoryReSpuListDTO;
import com.zhengqing.mall.model.dto.WebPmsCategorySpuRelationEditSortDTO;
import com.zhengqing.mall.model.dto.WebPmsCategorySpuRelationListDTO;
import com.zhengqing.mall.model.dto.WebPmsCategorySpuRelationPageDTO;
import com.zhengqing.mall.model.vo.PmsCategoryReSpuListVO;
import com.zhengqing.mall.model.vo.WebPmsCategorySpuRelationListVO;
import com.zhengqing.mall.model.vo.WebPmsCategorySpuRelationPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 商城-分类关联商品 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 16:04
 */
public interface PmsCategorySpuRelationMapper extends BaseMapper<PmsCategorySpuRelation> {

    /**
     * 分页列表
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/3/2 10:19
     */
    IPage<WebPmsCategorySpuRelationPageVO> selectDataPage(IPage<WebPmsCategorySpuRelationPageVO> page, @Param("filter") WebPmsCategorySpuRelationPageDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 16:04
     */
    List<WebPmsCategorySpuRelationListVO> selectDataList(@Param("filter") WebPmsCategorySpuRelationListDTO filter);

    /**
     * 批量更新商品显示状态
     *
     * @param idList 分类关联商品ids
     * @param isShow 是否显示：false->隐藏；true->显示
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchShow(@Param("idList") List<String> idList, @Param("isShow") boolean isShow);

    /**
     * 批量更新商品上下架状态
     *
     * @param idList 商品ids
     * @param isPut  是否上架：false->下架；true->上架
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchPut(@Param("idList") List<String> idList, @Param("isPut") boolean isPut);

    /**
     * 根据商品ids批量更新商品显示状态
     *
     * @param spuIdList 商品ids
     * @param isShow    是否显示：false->隐藏；true->显示
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchShowBySpuIds(@Param("spuIdList") List<String> spuIdList, @Param("isShow") boolean isShow);

    /**
     * 根据商品ids批量更新商品上下架状态
     *
     * @param spuIdList 商品ids
     * @param isPut     是否上架：false->下架；true->上架
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchPutBySpuIds(@Param("spuIdList") List<String> spuIdList, @Param("isPut") boolean isPut);

    /**
     * 批量排序
     *
     * @param list 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/9/15 14:16
     */
    void updateBatchSort(@Param("list") List<WebPmsCategorySpuRelationEditSortDTO> list);

    /**
     * 分类关联商品数据
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 15:33
     */
    List<PmsCategoryReSpuListVO> selectCategoryReSpu(@Param("filter") PmsCategoryReSpuListDTO filter);


    /**
     * 校验是否存在关联数据
     *
     * @param categoryId 分类id
     * @param spuId      商品id
     * @return 满足条件数
     * @author zhengqingya
     * @date 2022/3/17 11:26
     */
    int selectReData(@Param("categoryId") String categoryId, @Param("spuId") String spuId);

}
