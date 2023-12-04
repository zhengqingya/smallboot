package com.zhengqing.wf.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.wf.entity.WfForm;
import com.zhengqing.wf.mapper.WfFormMapper;
import com.zhengqing.wf.model.dto.WfFormDetailDTO;
import com.zhengqing.wf.model.dto.WfFormListDTO;
import com.zhengqing.wf.model.dto.WfFormPageDTO;
import com.zhengqing.wf.model.dto.WfFormSaveDTO;
import com.zhengqing.wf.model.vo.WfFormDetailVO;
import com.zhengqing.wf.model.vo.WfFormListVO;
import com.zhengqing.wf.model.vo.WfFormPageVO;
import com.zhengqing.wf.service.IWfFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 工作流-表单 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WfFormServiceImpl extends ServiceImpl<WfFormMapper, WfForm> implements IWfFormService {

    private final WfFormMapper wfFormMapper;

    @Override
    public IPage<WfFormPageVO> page(WfFormPageDTO params) {
        IPage<WfFormPageVO> result = this.wfFormMapper.selectDataPage(new Page<>(), params);
        List<WfFormPageVO> list = result.getRecords();
        list.forEach(WfFormPageVO::handleData);
        return result;
    }

    @Override
    public List<WfFormListVO> list(WfFormListDTO params) {
        return this.wfFormMapper.selectDataList(params);
    }

    @Override
    public WfFormDetailVO detail(WfFormDetailDTO params) {
        WfFormDetailVO detailData = this.wfFormMapper.detail(params);
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WfFormSaveDTO params) {
        WfForm.builder()
                .id(params.getId())
                .name(params.getName())
                .content(params.getContent())
                .remark(params.getRemark())
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Long id) {
        this.wfFormMapper.deleteById(id);
    }

}
