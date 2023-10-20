package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.system.entity.SysAppServiceConfig;
import com.zhengqing.system.mapper.SysAppServiceConfigMapper;
import com.zhengqing.system.model.dto.SysAppServiceConfigSaveDTO;
import com.zhengqing.system.model.vo.SysAppServiceConfigDetailVO;
import com.zhengqing.system.service.ISysAppServiceConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p> 系统管理-小程序服务商平台配置 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/20 16:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysAppServiceConfigServiceImpl extends ServiceImpl<SysAppServiceConfigMapper, SysAppServiceConfig> implements ISysAppServiceConfigService {

    private final SysAppServiceConfigMapper sysAppServiceConfigMapper;

    @Override
    public SysAppServiceConfigDetailVO detail() {
        SysAppServiceConfigDetailVO detailData = this.sysAppServiceConfigMapper.detail();
        Assert.notNull(detailData, "配置不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysAppServiceConfigSaveDTO params) {
        SysAppServiceConfig.builder()
                .id(params.getId())
                .name(params.getName())
                .componentAppId(params.getComponentAppId())
                .componentAppSecret(params.getComponentAppSecret())
                .tpToken(params.getTpToken())
                .encodingAesKey(params.getEncodingAesKey())
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.sysAppServiceConfigMapper.deleteById(id);
    }

}
