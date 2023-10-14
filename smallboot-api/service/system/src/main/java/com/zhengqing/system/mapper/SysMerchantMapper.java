package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysMerchant;
import com.zhengqing.system.model.dto.SysMerchantListDTO;
import com.zhengqing.system.model.dto.SysMerchantPageDTO;
import com.zhengqing.system.model.vo.SysMerchantListVO;
import com.zhengqing.system.model.vo.SysMerchantPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 系统管理-商户管理 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
public interface SysMerchantMapper extends BaseMapper<SysMerchant> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    IPage<SysMerchantPageVO> selectDataPage(IPage<SysMerchantPageVO> page, @Param("filter") SysMerchantPageDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    List<SysMerchantListVO> selectDataList(@Param("filter") SysMerchantListDTO filter);

    /**
     * 获取可以提审、发布的小程序appid
     *
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    List<SysMerchant> selectAppIdList();

}
