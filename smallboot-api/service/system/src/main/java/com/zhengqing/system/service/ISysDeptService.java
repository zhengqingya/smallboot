package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysDept;
import com.zhengqing.system.model.dto.SysDeptSaveDTO;
import com.zhengqing.system.model.dto.SysDeptTreeDTO;
import com.zhengqing.system.model.vo.SysDeptCheckVO;
import com.zhengqing.system.model.vo.SysDeptTreeVO;

import java.util.List;

/**
 * <p>  系统管理-部门 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 18:10
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 详情
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    SysDept detail(Integer id);

    /**
     * 树
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    List<SysDeptTreeVO> tree(SysDeptTreeDTO params);

    /**
     * 获取指定部门的顶级部门关联的 用户数 过期时间等等
     *
     * @param deptId 部门id
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    SysDeptCheckVO checkData(Integer deptId);

    /**
     * 获取小程序配置
     *
     * @param appId appId
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    SysDeptCheckVO configByAppId(String appId);

    /**
     * 获取指定部门下的子级部门（包含当前部门）
     *
     * @param deptId 部门id
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    List<Integer> getChildDeptIdList(Integer deptId);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return 主键ID
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    Integer addOrUpdateData(SysDeptSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    void deleteData(Integer id);

}
