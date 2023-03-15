package com.zhengqing.wxmp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.wxmp.entity.WxUser;
import com.zhengqing.wxmp.model.dto.WxUserDetailDTO;
import com.zhengqing.wxmp.model.dto.WxUserPageDTO;
import com.zhengqing.wxmp.model.vo.WxUserDetailVO;
import com.zhengqing.wxmp.model.vo.WxUserPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 微信公众号-用户 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:28
 */
public interface WxUserMapper extends BaseMapper<WxUser> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:28
     */
    IPage<WxUserPageVO> selectDataPage(IPage page, @Param("filter") WxUserPageDTO filter);

    /**
     * 详情
     *
     * @param filter 查询过滤参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/03/15 18:28
     */
    WxUserDetailVO detail(@Param("filter") WxUserDetailDTO filter);

}
