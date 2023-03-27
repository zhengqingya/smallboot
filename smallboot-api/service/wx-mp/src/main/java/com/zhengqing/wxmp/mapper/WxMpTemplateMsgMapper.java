package com.zhengqing.wxmp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.wxmp.entity.WxMpTemplateMsg;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgPageDTO;
import com.zhengqing.wxmp.model.vo.WxMpTemplateMsgPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 微信公众号-模板消息 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:29
 */
public interface WxMpTemplateMsgMapper extends BaseMapper<WxMpTemplateMsg> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:29
     */
    IPage<WxMpTemplateMsgPageVO> selectDataPage(IPage page, @Param("filter") WxMpTemplateMsgPageDTO filter);

}
