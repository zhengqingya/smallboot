package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.PmsCategory;

import java.util.List;

/**
 * <p>  商城-分类 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 14:01
 */
public interface OmsCategoryService<T> extends IService<PmsCategory> {


    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    PmsCategory detail(String id);

    /**
     * 批量删除分类数据
     *
     * @param idList ids
     * @return void
     * @author zhengqingya
     * @date 2022/3/2 11:39
     */
    void deleteBatch(List<String> idList);

}
