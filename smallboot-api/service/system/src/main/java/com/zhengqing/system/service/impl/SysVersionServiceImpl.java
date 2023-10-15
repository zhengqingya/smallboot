package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.util.AutoUpgradeVersionUtil;
import com.zhengqing.system.entity.SysVersion;
import com.zhengqing.system.mapper.SysVersionMapper;
import com.zhengqing.system.model.dto.SysVersionBaseDTO;
import com.zhengqing.system.model.dto.SysVersionSaveDTO;
import com.zhengqing.system.model.vo.SysVersionBaseVO;
import com.zhengqing.system.service.ISysVersionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 系统管理-版本记录 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/15 14:58
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysVersionServiceImpl extends ServiceImpl<SysVersionMapper, SysVersion> implements ISysVersionService {

    private final SysVersionMapper sysVersionMapper;

    @Override
    public IPage<SysVersionBaseVO> page(SysVersionBaseDTO params) {
        IPage<SysVersionBaseVO> result = this.sysVersionMapper.selectDataPage(new Page<>(), params);
        List<SysVersionBaseVO> list = result.getRecords();
        list.forEach(SysVersionBaseVO::handleData);
        return result;
    }

    @Override
    public SysVersionBaseVO lately() {
        List<SysVersionBaseVO> list = this.page(SysVersionBaseDTO.builder().build()).getRecords();
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysVersionSaveDTO params) {
        Integer id = params.getId();
        boolean isAdd = id == null;
        String version = params.getVersion();
        SysVersion sysVersion = SysVersion.builder()
                .id(id)
                .name(params.getName())
                .status(params.getStatus())
                .appAuditResultList(params.getAppAuditResultList())
                .remark(params.getRemark())
                .type(params.getType())
                .build();

        if (isAdd && StrUtil.isBlank(version)) {
            // 设置版本号
            SysVersionBaseVO latelyVersion = this.lately();
            version = "0.0.1";
            if (latelyVersion != null) {
                version = AutoUpgradeVersionUtil.autoUpgradeVersion(latelyVersion.getVersion());
            }
            sysVersion.setVersion(version);
        }
        
        sysVersion.insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.sysVersionMapper.deleteById(id);
    }

}
