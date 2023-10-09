package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysDept;
import com.zhengqing.system.model.dto.SysDeptSaveDTO;
import com.zhengqing.system.model.dto.SysDeptTreeDTO;
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
     * 树
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    List<SysDeptTreeVO> tree(SysDeptTreeDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    void addOrUpdateData(SysDeptSaveDTO params);

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
