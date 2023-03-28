package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.PmsSpu;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 商城-商品 Mapper 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
public interface PmsSpuMapper extends BaseMapper<PmsSpu> {

    /**
     * 获取tab条件
     *
     * @param filter 查询过滤参数
     * @return tab条件
     * @author zhengqingya
     * @date 2021/8/26 15:45
     */
    List<MallTabConditionListVO> selectTabCondition(@Param("filter") WebPmsSpuPageDTO filter);

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 15:33
     */
    IPage<WebPmsSpuPageVO> selectDataListByWeb(IPage<WebPmsSpuPageVO> page, @Param("filter") WebPmsSpuPageDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 15:33
     */
    List<WebPmsSpuPageVO> selectDataListByWeb(@Param("filter") WebPmsSpuPageDTO filter);

    /**
     * 查询商品类型
     *
     * @param idList 商品ids
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    List<PmsSpuTypeVO> selectDataTypeList(@Param("idList") List<String> idList);


    /**
     * 详情
     *
     * @param id 商品id
     * @return 商品详情
     * @author zhengqingya
     * @date 2021/8/20 9:18
     */
    WebPmsSpuDetailVO detailByWeb(@Param("id") String id);

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
     * 批量更新商品显示状态
     *
     * @param idList 商品ids
     * @param isShow 是否显示：false->隐藏；true->显示
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchShow(@Param("idList") List<String> idList, @Param("isShow") boolean isShow);

    /**
     * 批量更新预售状态
     *
     * @param filter 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchPresell(@Param("filter") WebPmsSpuEditPresellDTO filter);

    /**
     * 批量排序
     *
     * @param list 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/9/15 14:16
     */
    void updateBatchSort(@Param("list") List<WebPmsSpuEditSortListDTO> list);

    // ------------------------------------------------------------------------------------------------------

    /**
     * 列表
     *
     * @param page   分页参数
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 15:33
     */
    IPage<MiniPmsSpuPageVO> selectDataListByMini(IPage<MiniPmsSpuPageVO> page, @Param("filter") MiniPmsSpuPageDTO filter);

    /**
     * 列表-sku
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 15:33
     */
    List<PmsSkuVO> selectSkuList(@Param("filter") PmsSkuDTO filter);

    /**
     * 详情
     *
     * @param id 商品id
     * @return 商品详情
     * @author zhengqingya
     * @date 2021/8/20 9:18
     */
    MiniPmsSpuDetailVO detailByMini(@Param("id") String id);

}
