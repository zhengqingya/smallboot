package com.zhengqing.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.cms.entity.CmsJob;
import com.zhengqing.cms.model.dto.CmsJobPageDTO;
import com.zhengqing.cms.model.dto.CmsJobSaveDTO;
import com.zhengqing.cms.model.vo.CmsJobPageVO;

/**
 * <p>  内容管理-招聘岗位 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:08
 */
public interface ICmsJobService extends IService<CmsJob> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:08
     */
    IPage<CmsJobPageVO> page(CmsJobPageDTO params);

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:08
     */
    CmsJobPageVO detail(CmsJobPageDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/10 15:08
     */
    void addOrUpdateData(CmsJobSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/10 15:08
     */
    void deleteData(Integer id);

}
