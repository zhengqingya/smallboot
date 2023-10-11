package com.zhengqing.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.cms.entity.CmsJob;
import com.zhengqing.cms.model.dto.CmsJobBaseDTO;
import com.zhengqing.cms.model.dto.CmsJobSaveDTO;
import com.zhengqing.cms.model.vo.CmsJobBaseVO;

import java.util.List;
import java.util.Map;

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
    IPage<CmsJobBaseVO> page(CmsJobBaseDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:08
     */
    List<CmsJobBaseVO> list(CmsJobBaseDTO params);


    /**
     * 职位信息
     *
     * @param idList ids
     * @return id -> 职位信息
     * @author zhengqingya
     * @date 2021/08/26 11:44
     */
    Map<Integer, CmsJobBaseVO> map(List<Integer> idList);

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:08
     */
    CmsJobBaseVO detail(CmsJobBaseDTO params);

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
