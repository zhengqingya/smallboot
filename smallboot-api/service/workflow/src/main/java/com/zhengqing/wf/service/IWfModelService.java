package com.zhengqing.wf.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.wf.model.dto.WfModelPageDTO;
import com.zhengqing.wf.model.dto.WfModelSaveDTO;
import com.zhengqing.wf.model.vo.WfModelDetailVO;
import com.zhengqing.wf.model.vo.WfModelPageVO;

/**
 * <p>  工作流-流程模型 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
public interface IWfModelService {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    IPage<WfModelPageVO> page(WfModelPageDTO params);

    /**
     * 详情
     *
     * @param id 模型ID
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    WfModelDetailVO detail(String id);

    /**
     * 查询流程表单详细信息
     *
     * @param id 模型ID
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    String queryBpmnXmlById(String id);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    void addOrUpdateData(WfModelSaveDTO params);

}
