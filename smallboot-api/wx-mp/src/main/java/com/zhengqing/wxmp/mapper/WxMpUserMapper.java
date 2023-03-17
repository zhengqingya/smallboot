package com.zhengqing.wxmp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.wxmp.entity.WxMpUser;
import com.zhengqing.wxmp.model.dto.WxMpUserDetailDTO;
import com.zhengqing.wxmp.model.dto.WxMpUserPageDTO;
import com.zhengqing.wxmp.model.vo.WxMpUserDetailVO;
import com.zhengqing.wxmp.model.vo.WxMpUserPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 微信公众号-用户 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:28
 */
public interface WxMpUserMapper extends BaseMapper<WxMpUser> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:28
     */
    IPage<WxMpUserPageVO> selectDataPage(IPage page, @Param("filter") WxMpUserPageDTO filter);

    /**
     * 详情
     *
     * @param filter 查询过滤参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/03/15 18:28
     */
    WxMpUserDetailVO detail(@Param("filter") WxMpUserDetailDTO filter);

}
