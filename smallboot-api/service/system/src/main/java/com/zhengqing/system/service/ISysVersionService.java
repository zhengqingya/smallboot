package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysVersion;
import com.zhengqing.system.model.dto.SysVersionBaseDTO;
import com.zhengqing.system.model.dto.SysVersionSaveDTO;
import com.zhengqing.system.model.vo.SysVersionBaseVO;

/**
 * <p>  系统管理-版本记录 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/15 14:58
 */
public interface ISysVersionService extends IService<SysVersion> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/15 14:58
     */
    IPage<SysVersionBaseVO> page(SysVersionBaseDTO params);

    /**
     * 最近的一条版本记录
     *
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/15 14:58
     */
    SysVersionBaseVO lately();

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/15 14:58
     */
    void addOrUpdateData(SysVersionSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/15 14:58
     */
    void deleteData(Integer id);

}
