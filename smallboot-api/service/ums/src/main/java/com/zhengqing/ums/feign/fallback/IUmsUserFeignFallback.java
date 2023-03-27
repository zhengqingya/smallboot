package com.zhengqing.ums.feign.fallback;

import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.ums.feign.IUmsUserFeignApi;
import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.vo.UmsUserVO;
import org.springframework.stereotype.Component;

/**
 * <p> 用户 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:23
 */
@Component
public class IUmsUserFeignFallback implements IUmsUserFeignApi {

    @Override
    public ApiResult<UmsUserVO> getUser(Long id) {
        return ApiResult.busy();
    }

    @Override
    public ApiResult<UmsUserVO> getUserInfo(UmsUserDTO params) {
        return ApiResult.busy();
    }

}
