package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.PmsCategory;
import com.zhengqing.mall.model.dto.WebPmsCategoryEditShowDTO;
import com.zhengqing.mall.model.dto.WebPmsCategoryListDTO;
import com.zhengqing.mall.model.dto.WebPmsCategoryPageDTO;
import com.zhengqing.mall.model.dto.WebPmsCategorySaveDTO;
import com.zhengqing.mall.model.vo.WebPmsCategoryListVO;
import com.zhengqing.mall.model.vo.WebPmsCategoryPageVO;

import java.util.List;

/**
 * <p>  商城-分类 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
public interface WebOmsCategoryService extends OmsCategoryService<PmsCategory> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    IPage<WebPmsCategoryPageVO> page(WebPmsCategoryPageDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    List<WebPmsCategoryListVO> list(WebPmsCategoryListDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return 主键id
     * @author zhengqingya
     * @date 2021/08/20 17:38
     */
    String addOrUpdateData(WebPmsCategorySaveDTO params);

    /**
     * 删除数据 -- 批量
     *
     * @param idList 主键ids
     * @return void
     * @author zhengqingya
     * @date 2021/8/22 2:52 下午
     */
    void deleteBatchForBusiness(List<String> idList);

    /**
     * 批量更新显示状态
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchShow(WebPmsCategoryEditShowDTO params);

}
