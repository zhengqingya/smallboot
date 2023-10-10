package com.zhengqing.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.cms.entity.CmsJobApply;
import com.zhengqing.cms.model.dto.CmsJobApplyPageDTO;
import com.zhengqing.cms.model.dto.CmsJobApplySaveDTO;
import com.zhengqing.cms.model.vo.CmsJobApplyPageVO;

/**
 * <p>  内容管理-职位申请 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 18:11
 */
public interface ICmsJobApplyService extends IService<CmsJobApply> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 18:11
     */
    IPage<CmsJobApplyPageVO> page(CmsJobApplyPageDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/10 18:11
     */
    void addOrUpdateData(CmsJobApplySaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/10 18:11
     */
    void deleteData(Integer id);

}
