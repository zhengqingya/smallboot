package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.common.model.vo.PmsCategoryReSpuListVO;
import com.zhengqing.mall.entity.PmsCategorySpuRelation;

import java.util.List;
import java.util.Map;

/**
 * <p>  商城-分类关联商品 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 16:04
 */
public interface PmsCategorySpuRelationService<T> extends IService<PmsCategorySpuRelation> {

    /**
     * 分类关联商品数据
     *
     * @param categoryIdList 分类ids
     * @return 商品id -> 商品规格信息
     * @author zhengqingya
     * @date 2021/08/26 11:44
     */
    List<PmsCategoryReSpuListVO> listByCategoryIdList(List<String> categoryIdList);

    /**
     * 分类关联商品数据
     *
     * @param categoryIdList 分类ids
     * @return 分类id -> 商品
     * @author zhengqingya
     * @date 2021/08/26 11:44
     */
    Map<String, List<PmsCategoryReSpuListVO>> mapByCategoryIdList(List<String> categoryIdList);

}
