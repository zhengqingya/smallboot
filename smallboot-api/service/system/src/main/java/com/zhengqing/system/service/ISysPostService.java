package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysPost;
import com.zhengqing.system.model.dto.SysPostListDTO;
import com.zhengqing.system.model.dto.SysPostPageDTO;
import com.zhengqing.system.model.dto.SysPostSaveDTO;
import com.zhengqing.system.model.vo.SysPostListVO;
import com.zhengqing.system.model.vo.SysPostPageVO;

import java.util.List;

/**
 * <p>  系统管理-岗位 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 17:49
 */
public interface ISysPostService extends IService<SysPost> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 17:49
     */
    IPage<SysPostPageVO> page(SysPostPageDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 17:49
     */
    List<SysPostListVO> list(SysPostListDTO params);


    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/09 17:49
     */
    void addOrUpdateData(SysPostSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/09 17:49
     */
    void deleteData(Integer id);

}
