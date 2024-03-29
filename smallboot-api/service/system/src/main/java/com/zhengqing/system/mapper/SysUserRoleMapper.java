package com.zhengqing.system.mapper;

import com.zhengqing.common.db.mapper.MyBaseMapper;
import com.zhengqing.system.entity.SysUserRole;
import com.zhengqing.system.model.bo.SysUserReRoleIdListBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 系统管理 - 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:44
 */
public interface SysUserRoleMapper extends MyBaseMapper<SysUserRole> {

    /**
     * 根据角色id查询关联用户ids
     *
     * @param roleId 角色id
     * @return 用户ids
     * @author zhengqingya
     * @date 2022/6/14 12:39
     */
    @Select("SELECT user_id FROM t_sys_user_role WHERE role_id = #{roleId}")
    List<Integer> listUserId(@Param("roleId") Integer roleId);

    /**
     * 根据用户id查询关联角色ids
     *
     * @param userId 用户id
     * @return 角色ids
     * @author zhengqingya
     * @date 2022/6/14 12:39
     */
    List<Integer> listRoleId(@Param("userId") Integer userId);

    /**
     * 根据用户id查询关联角色ids
     *
     * @param userIdList 用户ids
     * @return 用户关联角色id
     * @author zhengqingya
     * @date 2022/6/14 12:39
     */
    List<SysUserReRoleIdListBO> selectListByUserIds(@Param("userIdList") List<Integer> userIdList);

}
