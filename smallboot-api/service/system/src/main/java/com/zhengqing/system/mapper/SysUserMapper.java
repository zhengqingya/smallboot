package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.model.dto.SysUserListDTO;
import com.zhengqing.system.model.dto.SysUserPermDTO;
import com.zhengqing.system.model.vo.SysUserListVO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统管理-用户基础信息表 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:05
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 列表分页
     *
     * @param page   分页参数
     * @param filter 过滤参数
     * @return 用户信息
     * @author zhengqingya
     * @date 2020/9/10 10:28
     */
    IPage<SysUserListVO> selectDataList(IPage<SysUserListDTO> page, @Param("filter") SysUserListDTO filter);

    /**
     * 列表
     *
     * @param filter 过滤参数
     * @return 用户信息
     * @author zhengqingya
     * @date 2020/9/10 10:34
     */
    List<SysUserListVO> selectDataList(@Param("filter") SysUserListDTO filter);

    /**
     * 查询用户信息
     *
     * @param filter 过滤参数
     * @return 用户信息
     * @author zhengqingya
     * @date 2020/9/21 16:18
     */
    SysUserPermVO selectUserPerm(@Param("filter") SysUserPermDTO filter);

    /**
     * 测试sql注入问题
     *
     * @param username 用户名
     * @return void
     * @author zhengqingya
     * @date 2023/2/28 11:07
     */
    SysUser testSqlmap(@Param("username") String username);

    /**
     * 查询部门id关联的用户数
     *
     * @param deptId 部门id
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:08
     */
    long selectUserNumByDeptId(@Param("deptId") Integer deptId);

}
