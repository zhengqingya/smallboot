package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysMerchant;
import com.zhengqing.system.model.dto.SysMerchantListDTO;
import com.zhengqing.system.model.dto.SysMerchantPageDTO;
import com.zhengqing.system.model.dto.SysMerchantSaveDTO;
import com.zhengqing.system.model.vo.SysMerchantListVO;
import com.zhengqing.system.model.vo.SysMerchantPageVO;

import java.util.List;

/**
 * <p>  系统管理-商户管理 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
public interface ISysMerchantService extends IService<SysMerchant> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    IPage<SysMerchantPageVO> page(SysMerchantPageDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    List<SysMerchantListVO> list(SysMerchantListDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    void addOrUpdateData(SysMerchantSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    void deleteData(Integer id);

    /**
     * 检查数据
     *
     * @param id 主键ID
     * @return 数据
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    SysMerchant checkData(Integer id);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    SysMerchant detail(Integer id);


}
