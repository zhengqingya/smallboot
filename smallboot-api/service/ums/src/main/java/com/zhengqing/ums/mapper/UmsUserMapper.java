package com.zhengqing.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhengqing.ums.entity.UmsUser;
import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.dto.WebUmsUserPageDTO;
import com.zhengqing.ums.model.vo.UmsUserVO;
import com.zhengqing.ums.model.vo.WebUmsUserPageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:05
 */
public interface UmsUserMapper extends BaseMapper<UmsUser> {

    /**
     * 详情
     *
     * @param userId 用户id
     * @return 用户信息
     * @author zhengqingya
     * @date 2022/6/10 16:00
     */
    @Select("SELECT * FROM ums_user WHERE id = #{userId}")
    UmsUserVO selectUser(@Param("userId") Long userId);

    /**
     * 详情
     *
     * @param filter 过滤参数
     * @return 用户信息
     * @author zhengqingya
     * @date 2022/6/10 16:00
     */
    UmsUserVO selectUserInfo(@Param("filter") UmsUserDTO filter);

    /**
     * 分页列表
     *
     * @param filter 过滤参数
     * @return 用户信息
     * @author zhengqingya
     * @date 2022/6/10 16:00
     */
    IPage<WebUmsUserPageVO> selectWebPage(Page<WebUmsUserPageVO> page, @Param("filter") WebUmsUserPageDTO filter);

}
