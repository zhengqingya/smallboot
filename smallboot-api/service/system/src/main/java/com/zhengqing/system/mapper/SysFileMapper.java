package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysFile;
import com.zhengqing.system.model.dto.SysFilePageDTO;
import com.zhengqing.system.model.vo.SysFilePageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 系统管理-文件上传记录 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/28 11:24
 */
public interface SysFileMapper extends BaseMapper<SysFile> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/09/28 11:24
     */
    IPage<SysFilePageVO> selectDataPage(IPage page, @Param("filter") SysFilePageDTO filter);

}
