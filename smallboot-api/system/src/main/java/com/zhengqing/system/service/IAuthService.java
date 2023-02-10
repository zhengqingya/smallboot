package com.zhengqing.common.auth.service;

import com.zhengqing.common.auth.model.dto.AuthLoginDTO;
import com.zhengqing.common.auth.model.vo.AuthLoginVO;

/**
 * <p>
 * 授权 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
public interface IAuthService {

    /**
     * 登录
     *
     * @param params 参数
     * @return token
     * @author zhengqingya
     * @date 2020/9/21 16:18
     */
    AuthLoginVO login(AuthLoginDTO params);

}
