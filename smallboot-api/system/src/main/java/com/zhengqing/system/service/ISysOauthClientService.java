package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysOauthClient;
import com.zhengqing.system.model.vo.SysOauthClientVO;

/**
 * <p>  oauth客户端 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/06/10 16:25
 */
public interface ISysOauthClientService extends IService<SysOauthClient> {

    /**
     * 详情
     *
     * @param clientId 客户端ID
     * @return 详情
     * @author zhengqingya
     * @date 2022/06/10 16:25
     */
    SysOauthClientVO detail(String clientId);


}
