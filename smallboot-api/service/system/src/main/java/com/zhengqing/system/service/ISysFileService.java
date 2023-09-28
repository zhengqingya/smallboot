package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysFile;
import com.zhengqing.system.model.dto.SysFilePageDTO;
import com.zhengqing.system.model.vo.SysFilePageVO;
import com.zhengqing.system.model.vo.SysFileVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>  系统管理-文件上传记录 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/28 11:24
 */
public interface ISysFileService extends IService<SysFile> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/09/28 11:24
     */
    IPage<SysFilePageVO> page(SysFilePageDTO params);

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/09/28 11:24
     */
    SysFileVO upload( MultipartFile file);

}
