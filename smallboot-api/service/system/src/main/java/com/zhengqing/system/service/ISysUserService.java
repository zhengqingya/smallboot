package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.model.dto.*;
import com.zhengqing.system.model.vo.SysUserListVO;
import com.zhengqing.system.model.vo.SysUserPermVO;

import java.util.List;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 列表分页
     *
     * @param params 查询参数
     * @return 用戶信息
     * @author zhengqingya
     * @date 2020/9/10 10:11
     */
    IPage<SysUserListVO> listPage(SysUserListDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 用戶信息
     * @author zhengqingya
     * @date 2020/9/10 10:11
     */
    List<SysUserListVO> list(SysUserListDTO params);

    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     * @author zhengqingya
     * @date 2020/9/10 10:53
     */
    SysUser detail(Integer userId);

    /**
     * 新增或更新
     *
     * @param params 提交参数
     * @return 用户id
     * @author zhengqingya
     * @date 2020/9/10 10:12
     */
    Integer addOrUpdateData(SysUserSaveDTO params);

    /**
     * 更新用户基本信息
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 10:12
     */
    void updateBaseInfo(SysUserSaveDTO params);

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 13:49
     */
    void deleteUser(Integer userId);

    /**
     * 修改用户密码
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 11:03
     */
    void updatePassword(SysUserUpdatePasswordDTO params);

    /**
     * 获取用户的基本信息+角色编码
     *
     * @param params 查询参数
     * @return 用户权限信息
     * @author zhengqingya
     * @date 2020/9/21 16:18
     */
    SysUserPermVO getUserPerm(SysUserPermDTO params);

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     * @author zhengqingya
     * @date 2020/9/21 16:18
     */
    SysUser getUserByUsername(String username);

}
