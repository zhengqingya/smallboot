package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.system.entity.SysLog;
import com.zhengqing.system.mapper.SysLogMapper;
import com.zhengqing.system.model.dto.SysLogPageDTO;
import com.zhengqing.system.model.dto.SysLogSaveDTO;
import com.zhengqing.system.model.vo.SysLogPageVO;
import com.zhengqing.system.service.ISysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 系统管理-操作日志 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/19 16:32
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    private final SysLogMapper sysLogMapper;

    @Override
    public IPage<SysLogPageVO> page(SysLogPageDTO params) {
        IPage<SysLogPageVO> result = this.sysLogMapper.selectDataPage(new Page<>(), params);
        List<SysLogPageVO> list = result.getRecords();
        list.forEach(SysLogPageVO::handleData);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysLogSaveDTO params) {
        Integer tenantId = TenantIdContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0;
        }
        SysLog.builder()
                .id(params.getId())
                .tenantId(tenantId)
                .type(params.getType())
                .apiMethod(params.getApiMethod())
                .apiMethodName(params.getApiMethodName())
                .apiHeader(params.getApiHeader())
                .operationName(params.getOperationName())
                .requestIp(params.getRequestIp())
                .requestUrl(params.getRequestUrl())
                .requestHttpMethod(params.getRequestHttpMethod())
                .requestParams(params.getRequestParams())
                .env(params.getEnv())
                .time(params.getTime())
                .status(params.getStatus())
                .responseResult(params.getResponseResult())
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Long id) {
        this.sysLogMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDataBeforeDay(Integer day) {
        Assert.isTrue(day > 0, "天数需要大于0");
        this.sysLogMapper.deleteDataBeforeDay(day);
    }
}
