package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysLog;
import com.zhengqing.system.model.dto.SysLogPageDTO;
import com.zhengqing.system.model.dto.SysLogSaveDTO;
import com.zhengqing.system.model.vo.SysLogPageVO;

/**
 * <p>  系统管理-操作日志 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/19 16:32
 */
public interface ISysLogService extends IService<SysLog> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/19 16:32
     */
    IPage<SysLogPageVO> page(SysLogPageDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/19 16:32
     */
    void addOrUpdateData(SysLogSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/19 16:32
     */
    void deleteData(Long id);

}
