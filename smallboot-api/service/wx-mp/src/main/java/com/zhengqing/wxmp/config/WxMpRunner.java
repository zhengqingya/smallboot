package com.zhengqing.wxmp.config;

import com.zhengqing.common.core.config.AppCommonRunner;
import com.zhengqing.wxmp.service.IWxMpAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 服务初始化之后，执行方法
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/5/22 19:29
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WxMpRunner extends AppCommonRunner {

    private final IWxMpAccountService wxAccountService;

    @Override
    public void run(String... args) throws Exception {
        super.appRun();

        // 初始化公众号配置
        this.wxAccountService.initWxMpConfigStorages();
    }

}
