package com.zhengqing.wxmp.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.wxmp.entity.WxMpTemplateMsg;
import com.zhengqing.wxmp.mapper.WxMpTemplateMsgMapper;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgDetailDTO;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgSaveDTO;
import com.zhengqing.wxmp.model.vo.WxMpTemplateMsgDetailVO;
import com.zhengqing.wxmp.model.vo.WxMpTemplateMsgPageVO;
import com.zhengqing.wxmp.service.IWxMpTemplateMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 微信公众号-模板消息 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxMpTemplateMsgServiceImpl extends ServiceImpl<WxMpTemplateMsgMapper, WxMpTemplateMsg> implements IWxMpTemplateMsgService {

    private final WxMpTemplateMsgMapper wxMpTemplateMsgMapper;

    @Override
    public IPage<WxMpTemplateMsgPageVO> page(WxMpTemplateMsgPageDTO params) {
        IPage<WxMpTemplateMsgPageVO> result = this.wxMpTemplateMsgMapper.selectDataPage(new Page<>(), params);
        List<WxMpTemplateMsgPageVO> list = result.getRecords();
        list.forEach(WxMpTemplateMsgPageVO::handleData);
        return result;
    }

    @Override
    public WxMpTemplateMsgDetailVO detail(WxMpTemplateMsgDetailDTO params) {
        WxMpTemplateMsgDetailVO detailData = this.wxMpTemplateMsgMapper.detail(params);
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WxMpTemplateMsgSaveDTO params) {
        Integer id = params.getId();
        String appId = params.getAppId();
        String tplId = params.getTplId();
        String title = params.getTitle();
        String content = params.getContent();

        WxMpTemplateMsg.builder()
                .id(id)
                .appId(appId)
                .tplId(tplId)
                .title(title)
                .content(content)
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.wxMpTemplateMsgMapper.deleteById(id);
    }

}
