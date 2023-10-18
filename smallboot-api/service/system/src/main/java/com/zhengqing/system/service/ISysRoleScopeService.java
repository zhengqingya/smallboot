package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysRoleScope;
import com.zhengqing.system.model.dto.SysRoleReScopeSaveDTO;
import com.zhengqing.system.model.dto.SysRoleScopeListDTO;
import com.zhengqing.system.model.vo.SysRoleScopeListVO;

import java.util.List;

/**
 * <p>  系统管理-角色&数据权限关联表 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/18 14:03
 */
public interface ISysRoleScopeService extends IService<SysRoleScope> {

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/18 14:03
     */
    List<SysRoleScopeListVO> list(SysRoleScopeListDTO params);

    /**
     * 保存
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/18 14:03
     */
    void saveScopeData(SysRoleReScopeSaveDTO params);

    /**
     * 根据角色id删除角色对应的所有关联权限
     *
     * @param roleId 角色id
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 18:08
     */
    void delByRoleId(Integer roleId);

    /**
     * 根据菜单id删除关联权限
     *
     * @param delMenuIdList 菜单id
     * @return void
     * @author zhengqingya
     * @date 2023/10/8 19:18
     */
    void delReMenuIdList(List<Integer> delMenuIdList);

    /**
     * 根据数据权限id删除关联权限
     *
     * @param scopeIdList 数据权限id
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 18:08
     */
    void delByScopeIdList(List<Integer> scopeIdList);

}
