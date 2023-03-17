package com.zhengqing.wxmp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.wxmp.entity.WxMpMsgAutoReply;
import com.zhengqing.wxmp.mapper.WxMpMsgAutoReplyMapper;
import com.zhengqing.wxmp.model.dto.WxMpMsgAutoReplyPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpMsgAutoReplySaveDTO;
import com.zhengqing.wxmp.model.vo.WxMpMsgAutoReplyPageVO;
import com.zhengqing.wxmp.service.IWxMpMsgAutoReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 微信公众号-消息自动回复 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxMpMsgAutoReplyServiceImpl extends ServiceImpl<WxMpMsgAutoReplyMapper, WxMpMsgAutoReply> implements IWxMpMsgAutoReplyService {

    private final WxMpMsgAutoReplyMapper wxMpMsgAutoReplyMapper;

    @Override
    public IPage<WxMpMsgAutoReplyPageVO> page(WxMpMsgAutoReplyPageDTO params) {
        IPage<WxMpMsgAutoReplyPageVO> result = this.wxMpMsgAutoReplyMapper.selectDataPage(new Page<>(), params);
        List<WxMpMsgAutoReplyPageVO> list = result.getRecords();
        list.forEach(WxMpMsgAutoReplyPageVO::handleData);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WxMpMsgAutoReplySaveDTO params) {
        Integer id = params.getId();
        String appId = params.getAppId();
        String name = params.getName();
        Byte type = params.getType();
        String matchValue = params.getMatchValue();
        Byte isExactMatch = params.getIsExactMatch();
        String replyType = params.getReplyType();
        String replyContent = params.getReplyContent();

        WxMpMsgAutoReply.builder()
                .id(id)
                .appId(appId)
                .name(name)
                .type(type)
                .matchValue(matchValue)
                .isExactMatch(isExactMatch)
                .replyType(replyType)
                .replyContent(replyContent)
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.wxMpMsgAutoReplyMapper.deleteById(id);
    }

}
