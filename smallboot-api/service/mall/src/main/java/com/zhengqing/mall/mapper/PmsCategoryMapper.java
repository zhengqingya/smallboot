package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.PmsCategory;
import com.zhengqing.mall.model.dto.MiniPmsCategoryReSpuListDTO;
import com.zhengqing.mall.model.dto.WebPmsCategoryListDTO;
import com.zhengqing.mall.model.dto.WebPmsCategoryPageDTO;
import com.zhengqing.mall.model.vo.MiniPmsCategoryReSpuListVO;
import com.zhengqing.mall.model.vo.WebPmsCategoryListVO;
import com.zhengqing.mall.model.vo.WebPmsCategoryPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 商城-分类 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 14:01
 */
public interface PmsCategoryMapper extends BaseMapper<PmsCategory> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    IPage<WebPmsCategoryPageVO> selectPageForWeb(IPage<WebPmsCategoryPageVO> page, @Param("filter") WebPmsCategoryPageDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    List<WebPmsCategoryListVO> selectDataList(@Param("filter") WebPmsCategoryListDTO filter);

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
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    List<MiniPmsCategoryReSpuListVO> selectReSpuDataListForMini(@Param("filter") MiniPmsCategoryReSpuListDTO filter);

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    IPage<MiniPmsCategoryReSpuListVO> selectReSpuDataListForMini(IPage<MiniPmsCategoryReSpuListVO> page, @Param("filter") MiniPmsCategoryReSpuListDTO filter);

}
