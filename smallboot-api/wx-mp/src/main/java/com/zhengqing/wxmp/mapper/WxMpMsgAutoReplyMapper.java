package com.zhengqing.wxmp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.wxmp.entity.WxMpMsgAutoReply;
import com.zhengqing.wxmp.model.dto.WxMpMsgAutoReplyListDTO;
import com.zhengqing.wxmp.model.dto.WxMpMsgAutoReplyPageDTO;
import com.zhengqing.wxmp.model.vo.WxMpMsgAutoReplyListVO;
import com.zhengqing.wxmp.model.vo.WxMpMsgAutoReplyPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 微信公众号-消息自动回复 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
public interface WxMpMsgAutoReplyMapper extends BaseMapper<WxMpMsgAutoReply> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:30
     */
    IPage<WxMpMsgAutoReplyPageVO> selectDataPage(IPage page, @Param("filter") WxMpMsgAutoReplyPageDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:30
     */
    List<WxMpMsgAutoReplyListVO> list(@Param("filter") WxMpMsgAutoReplyListDTO filter);


}
