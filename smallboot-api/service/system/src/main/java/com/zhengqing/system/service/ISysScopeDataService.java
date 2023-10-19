package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysScopeData;
import com.zhengqing.system.model.dto.SysScopeDataBaseDTO;
import com.zhengqing.system.model.dto.SysScopeDataSaveDTO;
import com.zhengqing.system.model.vo.SysScopeDataBaseVO;

import java.util.List;

/**
 * <p>  系统管理-数据权限 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/18 14:00
 */
public interface ISysScopeDataService extends IService<SysScopeData> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/18 14:00
     */
    IPage<SysScopeDataBaseVO> page(SysScopeDataBaseDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/18 14:00
     */
    List<SysScopeDataBaseVO> list(SysScopeDataBaseDTO params);

    /**
     * 树
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/18 14:00
     */
    List<SysScopeDataBaseVO> tree(SysScopeDataBaseDTO params);


    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/18 14:00
     */
    void addOrUpdateData(SysScopeDataSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/18 14:00
     */
    void deleteData(Integer id);


}
