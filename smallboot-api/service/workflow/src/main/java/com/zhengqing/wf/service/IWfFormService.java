package com.zhengqing.wf.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.wf.entity.WfForm;
import com.zhengqing.wf.model.dto.WfFormDetailDTO;
import com.zhengqing.wf.model.dto.WfFormListDTO;
import com.zhengqing.wf.model.dto.WfFormPageDTO;
import com.zhengqing.wf.model.dto.WfFormSaveDTO;
import com.zhengqing.wf.model.vo.WfFormDetailVO;
import com.zhengqing.wf.model.vo.WfFormListVO;
import com.zhengqing.wf.model.vo.WfFormPageVO;

import java.util.List;

/**
 * <p>  工作流-表单 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
public interface IWfFormService extends IService<WfForm> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    IPage<WfFormPageVO> page(WfFormPageDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    List<WfFormListVO> list(WfFormListDTO params);

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    WfFormDetailVO detail(WfFormDetailDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    void addOrUpdateData(WfFormSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    void deleteData(Long id);

}
