package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.model.dto.SysRoleBaseDTO;
import com.zhengqing.system.model.vo.SysRoleBaseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统管理 - 角色管理 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 15:01
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 列表分页
     *
     * @param page:
     * @param filter: 过滤参数
     * @return 角色信息
     * @author zhengqingya
     * @date 2020/9/10 18:07
     */
    // @Select({"<script>",
    // "SELECT * FROM t_sys_role WHERE is_deleted = 0",
    // "<if test=\"filter.name != null and filter.name != ''\">",
    // "AND name LIKE CONCAT( '%', #{filter.name} , '%' )",
    // "</if>",
    // "ORDER BY create_time DESC",
    // "</script>"})
    IPage<SysRoleBaseVO> selectDataList(IPage<SysRoleBaseVO> page, @Param("filter") SysRoleBaseDTO filter);

    /**
     * 列表
     *
     * @param filter: 过滤参数
     * @return 角色信息
     * @author zhengqingya
     * @date 2020/9/10 18:08
     */
    List<SysRoleBaseVO> selectDataList(@Param("filter") SysRoleBaseDTO filter);

    /**
     * 根据角色编码查询角色ID
     *
     * @param code 角色编码
     * @return 角色ID
     * @author zhengqingya
     * @date 2020/9/10 18:03
     */
    Integer selectRoleIdByCode(@Param("code") String code);

}
