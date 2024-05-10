package com.zhengqing.ums.feign;

import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.vo.UmsUserVO;

/**
 * <p> 用户 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:22
 */
public interface IUmsUserFeignApi {

    /**
     * 用户信息
     *
     * @param id 用户id
     * @return 用户信息
     * @author zhengqingya
     * @date 2022/6/1 15:42
     */
    UmsUserVO getUser(Long id);


    /**
     * 用户信息
     *
     * @param params 查询参数
     * @return 用户详情
     * @author zhengqingya
     * @date 2022/6/1 15:42
     */
    UmsUserVO getUserInfo(UmsUserDTO params);

}
