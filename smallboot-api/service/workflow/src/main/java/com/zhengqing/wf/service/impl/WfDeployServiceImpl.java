package com.zhengqing.wf.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.wf.model.dto.WfDeployPageDTO;
import com.zhengqing.wf.model.vo.WfDeployPageVO;
import com.zhengqing.wf.service.IWfDeployService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.springframework.stereotype.Service;

/**
 * <p> 工作流-流程部署 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WfDeployServiceImpl implements IWfDeployService {

    private final RepositoryService repositoryService;

    @Override
    public IPage<WfDeployPageVO> page(WfDeployPageDTO params) {
        return null;
    }


}
