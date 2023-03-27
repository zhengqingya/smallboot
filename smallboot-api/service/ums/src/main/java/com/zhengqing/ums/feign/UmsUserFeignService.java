package com.zhengqing.ums.feign;

import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.vo.UmsUserVO;
import com.zhengqing.ums.service.IUmsUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * <p> 用户 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:25
 */
@ApiIgnore
@Api(tags = "用户")
@RestController
//@RequestMapping(RpcConstant.RPC_API_PREFIX_UMS + "/user")
public class UmsUserFeignService implements IUmsUserFeignApi {

    @Resource
    private IUmsUserService sysUserService;

    @Override
    public ApiResult<UmsUserVO> getUser(Long id) {
        return ApiResult.ok(this.sysUserService.getUser(id));
    }

    @Override
    public ApiResult<UmsUserVO> getUserInfo(UmsUserDTO params) {
        return ApiResult.ok(this.sysUserService.getUserInfo(params));
    }

}
