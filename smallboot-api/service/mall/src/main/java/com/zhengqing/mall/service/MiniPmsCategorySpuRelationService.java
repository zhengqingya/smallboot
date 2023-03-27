package com.zhengqing.mall.service;

import com.zhengqing.mall.entity.PmsCategorySpuRelation;

/**
 * <p>  商城-分类关联商品 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
public interface MiniPmsCategorySpuRelationService extends PmsCategorySpuRelationService<PmsCategorySpuRelation> {

    /**
     * 校验是否存在关联数据
     *
     * @param categoryId 分类id
     * @param spuId      商品id
     * @return void
     * @author zhengqingya
     * @date 2022/3/17 11:26
     */
    void checkExistReData(String categoryId, String spuId);

}
