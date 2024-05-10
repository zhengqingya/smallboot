package com.zhengqing.ums.feign;

import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.vo.UmsUserVO;
import com.zhengqing.ums.service.IUmsUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> 用户 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:25
 */
@Service
public class UmsUserFeignService implements IUmsUserFeignApi {

    @Resource
    private IUmsUserService sysUserService;

    @Override
    public UmsUserVO getUser(Long id) {
        return this.sysUserService.getUser(id);
    }

    @Override
    public UmsUserVO getUserInfo(UmsUserDTO params) {
        return this.sysUserService.getUserInfo(params);
    }

}
