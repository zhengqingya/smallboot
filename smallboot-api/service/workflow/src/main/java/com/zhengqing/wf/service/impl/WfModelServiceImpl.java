package com.zhengqing.wf.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhengqing.common.flowable.util.FlowableUtil;
import com.zhengqing.wf.enums.WfFormTypeEnum;
import com.zhengqing.wf.model.bo.WfFormConfigBO;
import com.zhengqing.wf.model.dto.WfFormDetailDTO;
import com.zhengqing.wf.model.dto.WfModelPageDTO;
import com.zhengqing.wf.model.dto.WfModelSaveDTO;
import com.zhengqing.wf.model.vo.WfFormDetailVO;
import com.zhengqing.wf.model.vo.WfModelDetailVO;
import com.zhengqing.wf.model.vo.WfModelMetaInfoVO;
import com.zhengqing.wf.model.vo.WfModelPageVO;
import com.zhengqing.wf.service.IWfFormService;
import com.zhengqing.wf.service.IWfModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> 工作流-流程模型 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WfModelServiceImpl implements IWfModelService {

    private final RepositoryService repositoryService;
    private final IWfFormService iWfFormService;

    @Override
    public IPage<WfModelPageVO> page(WfModelPageDTO params) {
        String key = params.getKey();
        String name = params.getName();

        // 查询条件
        ModelQuery modelQuery = this.repositoryService.createModelQuery();
        if (StrUtil.isNotBlank(key)) {
            modelQuery.modelKey(key);
        }
        if (StrUtil.isNotBlank(name)) {
            modelQuery.modelNameLike("%" + name + "%");
        }

        // 查询数据
        long pageTotal = modelQuery.count();
        Page<WfModelPageVO> page = new Page<>();
        if (pageTotal <= 0) {
            return page;
        }

        List<Model> modelList = modelQuery
                .latestVersion()
                .orderByCreateTime()
                .desc()
                .listPage(params.getOffset(), params.getPageSize());

        List<WfModelPageVO> list = new ArrayList<>(modelList.size());
        modelList.forEach(model -> {
            WfModelPageVO wfModelPageVO = WfModelPageVO.builder()
                    .id(model.getId())
                    .name(model.getName())
                    .key(model.getKey())
                    .category(model.getCategory())
                    .version(model.getVersion())
                    .createTime(model.getCreateTime())
                    .metaInfoObj(JSONUtil.toBean(model.getMetaInfo(), WfModelMetaInfoVO.class))
                    .build();
            list.add(wfModelPageVO);
        });

        page.setRecords(list);
        page.setTotal(pageTotal);
        return page;
    }

    @Override
    public WfModelDetailVO detail(String id) {
        Model model = this.repositoryService.getModel(id);
        if (ObjectUtil.isNull(model)) {
            throw new RuntimeException("流程模型不存在！");
        }

        WfFormConfigBO formConfigObj = null;
        WfModelMetaInfoVO wfModelMetaInfoVO = JSONUtil.toBean(model.getMetaInfo(), WfModelMetaInfoVO.class);
        if (wfModelMetaInfoVO != null) {
            if (WfFormTypeEnum.PROCESS.getType().equals(wfModelMetaInfoVO.getFormType())) {
                // 查询表单内容
                WfFormDetailVO wfFormDetailVO = this.iWfFormService.detail(WfFormDetailDTO.builder().id(wfModelMetaInfoVO.getFormId()).build());
                formConfigObj = wfFormDetailVO.getConfig();
            }
        }

        return WfModelDetailVO.builder()
                .id(model.getId())
                .name(model.getName())
                .key(model.getKey())
                .category(model.getCategory())
                .version(model.getVersion())
                .createTime(model.getCreateTime())
                .metaInfoObj(wfModelMetaInfoVO)
                .bpmnXml(this.queryBpmnXmlById(id))
                .formConfigObj(formConfigObj)
                .build();
    }

    @Override
    public String queryBpmnXmlById(String id) {
        byte[] bpmnBytes = this.repositoryService.getModelEditorSource(id);
        return StrUtil.utf8Str(bpmnBytes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WfModelSaveDTO params) {
        String id = params.getId();
        boolean isAdd = StrUtil.isBlank(id);
        String bpmnXml = params.getBpmnXml();

        Model model;
        if (isAdd) {
            model = this.repositoryService.newModel();
            model.setName(params.getName());
        } else {
            BpmnModel bpmnModel = FlowableUtil.getBpmnModel(bpmnXml);
            Assert.isFalse(ObjectUtil.isNotEmpty(bpmnModel), "获取模型设计失败！");

            String processName = bpmnModel.getMainProcess().getName();

            // 获取开始节点
            StartEvent startEvent = FlowableUtil.getStartEvent(bpmnModel);
            Assert.isFalse(ObjectUtil.isNull(startEvent), "开始节点不存在，请检查流程设计是否有误！");
            Assert.isFalse(StrUtil.isBlank(startEvent.getFormKey()), "请配置流程开始节点的表单key");

            if (params.getIsNewVersion()) {
                // 新流程
                model = this.repositoryService.newModel();
            } else {
                model = this.repositoryService.getModel(id);
                Assert.isFalse(ObjectUtil.isNull(model), "流程模型不存在！");
                model.setVersion(model.getVersion() + 1);
            }

            model.setName(processName);
        }


        model.setKey(params.getKey());
        model.setCategory(params.getCategory());
        model.setMetaInfo(JSONUtil.toJsonStr(params.getMetaInfoObj()));

        // 保存流程模型
        this.repositoryService.saveModel(model);

        // 保存 BPMN XML
        if (!isAdd) {
            byte[] bpmnXmlBytes = StrUtil.bytes(bpmnXml, StandardCharsets.UTF_8);
            this.repositoryService.addModelEditorSource(model.getId(), bpmnXmlBytes);
        }
    }


}
