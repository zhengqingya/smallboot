package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysUserRole;
import com.zhengqing.system.model.dto.SysUserRoleSaveDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统管理 - 用户角色管理 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 11:52
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 新增或更新
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 14:29
     */
    void addOrUpdateData(SysUserRoleSaveDTO params);


    /**
     * 根据用户id查询关联角色ids
     *
     * @param userId 用户id
     * @return 角色ids
     * @author zhengqingya
     * @date 2022/6/14 12:39
     */
    List<Integer> listRoleId(Integer userId);

    /**
     * 根据用户ids查询关联角色ids
     *
     * @param userIdList 用户ids
     * @return 用户id -> 角色ids
     * @author zhengqingya
     * @date 2022/6/14 12:39
     */
    Map<Integer, List<Integer>> mapRoleId(List<Integer> userIdList);


    /**
     * 删除用户id关联角色ids
     *
     * @param userId 用户id
     * @return void
     * @author zhengqingya
     * @date 2022/6/14 12:39
     */
    void delByUserId(Integer userId);

}
