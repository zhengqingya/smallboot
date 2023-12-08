package com.zhengqing.wf.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.wf.model.dto.WfDeployPageDTO;
import com.zhengqing.wf.model.vo.WfDeployPageVO;

/**
 * <p>  工作流-流程部署 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
public interface IWfDeployService {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    IPage<WfDeployPageVO> page(WfDeployPageDTO params);


}
