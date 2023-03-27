package com.zhengqing.ums.feign;

import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.vo.UmsUserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:22
 */
//@FeignClient(value = RpcConstant.RPC_UMS,
//        path = RpcConstant.RPC_API_PREFIX_UMS + "/user",
//        contextId = "IUmsUserFeignApi",
//        fallback = IUmsUserFeignFallback.class)
public interface IUmsUserFeignApi {

    /**
     * 用户信息
     *
     * @param id 用户id
     * @return 用户信息
     * @author zhengqingya
     * @date 2022/6/1 15:42
     */
    @GetMapping("getUser/{id}")
    ApiResult<UmsUserVO> getUser(@PathVariable Long id);

    /**
     * 用户信息
     *
     * @param params 查询参数
     * @return 用户详情
     * @author zhengqingya
     * @date 2022/6/1 15:42
     */
    @GetMapping("getUserInfo")
    ApiResult<UmsUserVO> getUserInfo(@ModelAttribute UmsUserDTO params);

}
