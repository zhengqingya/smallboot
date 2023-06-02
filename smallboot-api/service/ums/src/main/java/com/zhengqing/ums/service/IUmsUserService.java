package com.zhengqing.ums.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.ums.entity.UmsUser;
import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.dto.UmsUserWxLoginDTO;
import com.zhengqing.ums.model.dto.WebUmsUserPageDTO;
import com.zhengqing.ums.model.vo.UmsUserVO;
import com.zhengqing.ums.model.vo.WebUmsUserPageVO;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
public interface IUmsUserService extends IService<UmsUser> {

    /**
     * 详情
     *
     * @param id 用户id
     * @return 用户信息
     * @author zhengqingya
     * @date 2022/6/10 16:00
     */
    UmsUser detail(Long id);

    /**
     * 详情
     *
     * @param id 用户id
     * @return 用户信息
     * @author zhengqingya
     * @date 2022/6/10 16:00
     */
    UmsUserVO getUser(Long id);

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 用户信息
     * @author zhengqingya
     * @date 2022/6/10 16:00
     */
    UmsUserVO getUserInfo(UmsUserDTO params);


    /**
     * 微信小程序登录
     *
     * @param params 认证
     * @return 用户信息
     * @author zhengqingya
     * @date 2022/12/7 11:05
     */
    UmsUserVO wxLogin(UmsUserWxLoginDTO params);

    UmsUserVO getPhone(String code);

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 用户信息
     * @author zhengqingya
     * @date 2022/6/10 16:00
     */
    IPage<WebUmsUserPageVO> page(WebUmsUserPageDTO params);

}
