package com.zhengqing.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.cms.entity.CmsJobCategory;
import com.zhengqing.cms.model.dto.CmsJobCategoryListDTO;
import com.zhengqing.cms.model.dto.CmsJobCategoryPageDTO;
import com.zhengqing.cms.model.dto.CmsJobCategorySaveDTO;
import com.zhengqing.cms.model.vo.CmsJobCategoryListVO;
import com.zhengqing.cms.model.vo.CmsJobCategoryPageVO;

import java.util.List;

/**
 * <p>  内容管理-招聘岗位分类 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:09
 */
public interface ICmsJobCategoryService extends IService<CmsJobCategory> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:09
     */
    IPage<CmsJobCategoryPageVO> page(CmsJobCategoryPageDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/10/10 15:09
     */
    List<CmsJobCategoryListVO> list(CmsJobCategoryListDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/10 15:09
     */
    void addOrUpdateData(CmsJobCategorySaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/10 15:09
     */
    void deleteData(Integer id);

}
