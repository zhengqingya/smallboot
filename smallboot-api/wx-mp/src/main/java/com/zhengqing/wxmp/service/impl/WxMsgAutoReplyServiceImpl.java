package com.zhengqing.wxmp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.wxmp.entity.WxMsgAutoReply;
import com.zhengqing.wxmp.mapper.WxMsgAutoReplyMapper;
import com.zhengqing.wxmp.model.dto.WxMsgAutoReplyPageDTO;
import com.zhengqing.wxmp.model.dto.WxMsgAutoReplySaveDTO;
import com.zhengqing.wxmp.model.vo.WxMsgAutoReplyPageVO;
import com.zhengqing.wxmp.service.IWxMsgAutoReplyService;
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
public class WxMsgAutoReplyServiceImpl extends ServiceImpl<WxMsgAutoReplyMapper, WxMsgAutoReply> implements IWxMsgAutoReplyService {

    private final WxMsgAutoReplyMapper wxMsgAutoReplyMapper;

    @Override
    public IPage<WxMsgAutoReplyPageVO> page(WxMsgAutoReplyPageDTO params) {
        IPage<WxMsgAutoReplyPageVO> result = this.wxMsgAutoReplyMapper.selectDataPage(new Page<>(), params);
        List<WxMsgAutoReplyPageVO> list = result.getRecords();
        list.forEach(WxMsgAutoReplyPageVO::handleData);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WxMsgAutoReplySaveDTO params) {
        Integer id = params.getId();
        String appId = params.getAppId();
        String name = params.getName();
        Byte type = params.getType();
        String matchValue = params.getMatchValue();
        Byte isExactMatch = params.getIsExactMatch();
        String replyType = params.getReplyType();
        String replyContent = params.getReplyContent();

        WxMsgAutoReply.builder()
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
        this.wxMsgAutoReplyMapper.deleteById(id);
    }

}
