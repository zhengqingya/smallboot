package com.zhengqing.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.cms.entity.CmsJobTag;
import com.zhengqing.cms.model.dto.CmsJobTagListDTO;
import com.zhengqing.cms.model.dto.CmsJobTagPageDTO;
import com.zhengqing.cms.model.dto.CmsJobTagSaveDTO;
import com.zhengqing.cms.model.vo.CmsJobTagListVO;
import com.zhengqing.cms.model.vo.CmsJobTagPageVO;

import java.util.List;
import java.util.Map;

/**
 * <p>  内容管理-招聘岗位标签 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:10
 */
public interface ICmsJobTagService extends IService<CmsJobTag> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:10
     */
    IPage<CmsJobTagPageVO> page(CmsJobTagPageDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/10/10 15:09
     */
    List<CmsJobTagListVO> list(CmsJobTagListDTO params);

    /**
     * 标签数据
     *
     * @param idList 标签ids
     * @return 标签id -> 名称
     * @author zhengqingya
     * @date 2021/08/26 11:44
     */
    Map<Integer, String> map(List<Integer> idList);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/10 15:10
     */
    void addOrUpdateData(CmsJobTagSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/10 15:10
     */
    void deleteData(Integer id);

}
