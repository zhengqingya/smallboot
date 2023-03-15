package com.zhengqing.wxmp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.wxmp.entity.WxTemplateMsg;
import com.zhengqing.wxmp.model.dto.WxTemplateMsgDetailDTO;
import com.zhengqing.wxmp.model.dto.WxTemplateMsgPageDTO;
import com.zhengqing.wxmp.model.vo.WxTemplateMsgDetailVO;
import com.zhengqing.wxmp.model.vo.WxTemplateMsgPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 微信公众号-模板消息 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:29
 */
public interface WxTemplateMsgMapper extends BaseMapper<WxTemplateMsg> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:29
     */
    IPage<WxTemplateMsgPageVO> selectDataPage(IPage page, @Param("filter") WxTemplateMsgPageDTO filter);

    /**
     * 详情
     *
     * @param filter 查询过滤参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/03/15 18:29
     */
    WxTemplateMsgDetailVO detail(@Param("filter") WxTemplateMsgDetailDTO filter);

}
