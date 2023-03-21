package com.zhengqing.wxmp.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.wxmp.entity.WxMpTemplateMsg;
import com.zhengqing.wxmp.mapper.WxMpTemplateMsgMapper;
import com.zhengqing.wxmp.model.bo.WxMpTemplateMsgDataBO;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgSendDTO;
import com.zhengqing.wxmp.model.vo.WxMpTemplateMsgPageVO;
import com.zhengqing.wxmp.service.IWxMpTemplateMsgService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    private final WxMpService wxMpService;

    @Override
    public IPage<WxMpTemplateMsgPageVO> page(WxMpTemplateMsgPageDTO params) {
        IPage<WxMpTemplateMsgPageVO> result = this.wxMpTemplateMsgMapper.selectDataPage(new Page<>(), params);
        List<WxMpTemplateMsgPageVO> list = result.getRecords();
        list.forEach(WxMpTemplateMsgPageVO::handleData);
        return result;
    }

    @Override
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public void sync(String appId) {
        // 1、删除对应公众号关联的模板数据
        this.wxMpTemplateMsgMapper.delete(
                new LambdaQueryWrapper<WxMpTemplateMsg>().eq(WxMpTemplateMsg::getAppId, appId)
        );

        // 2、切换到对应公众号
        this.wxMpService.switchover(appId);

        // 3、查询模板数据
        List<WxMpTemplate> wxMpTemplateList = this.wxMpService.getTemplateMsgService().getAllPrivateTemplate();

        // 4、保存db
        List<WxMpTemplateMsg> saveList = wxMpTemplateList.stream().map(
                item -> {
                    WxMpTemplateMsg templateMsg = WxMpTemplateMsg.builder()
                            .appId(appId)
                            .tplId(item.getTemplateId())
                            .title(item.getTitle())
                            .content(item.getContent())
                            .build();
                    return templateMsg.handleData();
                }
        ).collect(Collectors.toList());
        this.saveBatch(saveList);
    }

    @Override
    @SneakyThrows({Exception.class})
    @Transactional(rollbackFor = Exception.class)
    public void sendMsg(WxMpTemplateMsgSendDTO params) {
        Integer id = params.getId();
        String appId = params.getAppId();
        String tplId = params.getTplId();
        String title = params.getTitle();
        String content = params.getContent();
        List<WxMpTemplateMsgDataBO> dataList = params.getDataList();
        String openid = params.getOpenid();

        // 1、保存数据
        WxMpTemplateMsg.builder()
                .id(id)
                .appId(appId)
                .tplId(tplId)
                .title(title)
                .content(content)
                .dataList(dataList)
                .build()
                .insertOrUpdate();

        // 2、发送消息
        this.wxMpService.switchover(appId);
        this.wxMpService.getTemplateMsgService().sendTemplateMsg(
                WxMpTemplateMessage.builder()
                        .templateId(tplId)
                        .data(JSONUtil.toList(JSONUtil.toJsonStr(dataList), WxMpTemplateData.class))
                        .toUser(openid)
                        .build()
        );
    }

}
