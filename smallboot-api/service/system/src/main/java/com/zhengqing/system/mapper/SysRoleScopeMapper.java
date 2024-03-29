package com.zhengqing.system.mapper;

import com.zhengqing.common.base.model.bo.ScopeDataBO;
import com.zhengqing.common.db.mapper.MyBaseMapper;
import com.zhengqing.system.entity.SysRoleScope;
import com.zhengqing.system.model.dto.SysRoleScopeListDTO;
import com.zhengqing.system.model.vo.SysRoleScopeListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 系统管理-角色&数据权限关联表 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/18 14:03
 */
public interface SysRoleScopeMapper extends MyBaseMapper<SysRoleScope> {

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/18 14:03
     */
    List<SysRoleScopeListVO> selectDataList(@Param("filter") SysRoleScopeListDTO filter);

    /**
     * 根据角色拿到关联的数据权限
     *
     * @param roleIdList 角色ids
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/18 14:03
     */
    List<ScopeDataBO> selectScopeListReRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    /**
     * 删除租户关联权限
     *
     * @param delMenuIdList 菜单id
     * @return void
     * @author zhengqingya
     * @date 2023/10/8 19:18
     */
    void delReMenuIdList(@Param("delMenuIdList") List<Integer> delMenuIdList);


}
