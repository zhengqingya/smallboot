package com.zhengqing.wxmp.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.wxmp.entity.WxTemplateMsg;
import com.zhengqing.wxmp.mapper.WxTemplateMsgMapper;
import com.zhengqing.wxmp.model.dto.WxTemplateMsgDetailDTO;
import com.zhengqing.wxmp.model.dto.WxTemplateMsgPageDTO;
import com.zhengqing.wxmp.model.dto.WxTemplateMsgSaveDTO;
import com.zhengqing.wxmp.model.vo.WxTemplateMsgDetailVO;
import com.zhengqing.wxmp.model.vo.WxTemplateMsgPageVO;
import com.zhengqing.wxmp.service.IWxTemplateMsgService;
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
public class WxTemplateMsgServiceImpl extends ServiceImpl<WxTemplateMsgMapper, WxTemplateMsg> implements IWxTemplateMsgService {

    private final WxTemplateMsgMapper wxTemplateMsgMapper;

    @Override
    public IPage<WxTemplateMsgPageVO> page(WxTemplateMsgPageDTO params) {
        IPage<WxTemplateMsgPageVO> result = this.wxTemplateMsgMapper.selectDataPage(new Page<>(), params);
        List<WxTemplateMsgPageVO> list = result.getRecords();
        list.forEach(WxTemplateMsgPageVO::handleData);
        return result;
    }

    @Override
    public WxTemplateMsgDetailVO detail(WxTemplateMsgDetailDTO params) {
        WxTemplateMsgDetailVO detailData = this.wxTemplateMsgMapper.detail(params);
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WxTemplateMsgSaveDTO params) {
        Integer id = params.getId();
        String appId = params.getAppId();
        String tplId = params.getTplId();
        String title = params.getTitle();
        String content = params.getContent();

        WxTemplateMsg.builder()
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
        this.wxTemplateMsgMapper.deleteById(id);
    }

}
