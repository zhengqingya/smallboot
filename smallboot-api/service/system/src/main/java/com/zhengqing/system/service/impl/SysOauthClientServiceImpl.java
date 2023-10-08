package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.system.entity.SysOauthClient;
import com.zhengqing.system.mapper.SysOauthClientMapper;
import com.zhengqing.system.model.vo.SysOauthClientVO;
import com.zhengqing.system.service.ISysOauthClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p> oauth客户端 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/06/10 16:25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysOauthClientServiceImpl extends ServiceImpl<SysOauthClientMapper, SysOauthClient> implements ISysOauthClientService {

    private final SysOauthClientMapper sysOauthClientMapper;

    @Override
    public SysOauthClientVO detail(String clientId) {
        SysOauthClientVO sysOauthClientVO = this.sysOauthClientMapper.selectClient(clientId);
        Assert.notNull(sysOauthClientVO, "该数据不存在！");
        return sysOauthClientVO;
    }

}
