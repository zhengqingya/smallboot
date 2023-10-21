package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.common.base.enums.SysRoleCodeEnum;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.model.dto.SysRoleBaseDTO;
import com.zhengqing.system.model.dto.SysRoleSaveDTO;
import com.zhengqing.system.model.vo.SysRoleBaseVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统管理 - 角色管理 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 15:01
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 列表分页
     *
     * @param params 查询参数
     * @return 角色信息
     * @author zhengqingya
     * @date 2020/9/10 14:44
     */
    IPage<SysRoleBaseVO> listPage(SysRoleBaseDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 角色信息
     * @author zhengqingya
     * @date 2020/9/10 14:45
     */
    List<SysRoleBaseVO> list(SysRoleBaseDTO params);

    /**
     * 详情
     *
     * @param roleId 角色ID
     * @return 角色信息
     * @author zhengqingya
     * @date 2020/9/10 14:45
     */
    SysRole detail(Integer roleId);

    /**
     * 角色id -> 角色名
     *
     * @param roleIdList 角色ids
     * @return 角色id -> 角色名
     * @author zhengqingya
     * @date 2020/9/10 14:44
     */
    Map<Integer, String> mapByRoleIdList(List<Integer> roleIdList);


    /**
     * 树
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2020/9/10 14:44
     */
    List<SysRoleBaseVO> tree(SysRoleBaseDTO params);

    /**
     * 获取指定角色下的子级角色（包含当前角色）
     *
     * @param roleId 角色id
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    List<Integer> getChildRoleIdList(Integer roleId);

    /**
     * 新增或更新
     *
     * @param params 提交参数
     * @return 角色id
     * @author zhengqingya
     * @date 2020/9/10 14:45
     */
    Integer addOrUpdateData(SysRoleSaveDTO params);

    /**
     * 根据角色id删除角色与关联菜单权限
     *
     * @param roleId 角色id
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 18:03
     */
    void deleteRoleAndRoleMenu(Integer roleId);

    /**
     * 根据code查询角色ID
     *
     * @param sysRoleCodeEnum 角色code
     * @return 角色ID
     * @author zhengqingya
     * @date 2020/9/10 18:03
     */
    Integer getRoleIdByCode(SysRoleCodeEnum sysRoleCodeEnum);

}
