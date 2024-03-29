package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysTenant;
import com.zhengqing.system.model.dto.SysTenantListDTO;
import com.zhengqing.system.model.dto.SysTenantPageDTO;
import com.zhengqing.system.model.dto.SysTenantSaveDTO;
import com.zhengqing.system.model.vo.SysTenantConfigVO;
import com.zhengqing.system.model.vo.SysTenantListVO;
import com.zhengqing.system.model.vo.SysTenantPageVO;

import java.util.List;

/**
 * <p>  系统管理-租户信息 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 15:40
 */
public interface ISysTenantService extends IService<SysTenant> {

    /**
     * 详情
     *
     * @param id 租户id
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/08 15:40
     */
    SysTenant detail(Integer id);

    /**
     * 小程序查看配置使用
     *
     * @param id 租户id
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/08 15:40
     */
    SysTenantConfigVO configForApp(Integer id);

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/08 15:40
     */
    IPage<SysTenantPageVO> page(SysTenantPageDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/08 15:40
     */
    List<SysTenantListVO> list(SysTenantListDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/08 15:40
     */
    void addOrUpdateData(SysTenantSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/08 15:40
     */
    void deleteData(Integer id);

    /**
     * 校验数据
     *
     * @param tenantId 租户ID
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    SysTenant checkData(Integer tenantId);

}
