package com.zhengqing.wxmp.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.wxmp.entity.WxMpUser;
import com.zhengqing.wxmp.mapper.WxMpUserMapper;
import com.zhengqing.wxmp.model.dto.WxMpUserDetailDTO;
import com.zhengqing.wxmp.model.dto.WxMpUserPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpUserSaveDTO;
import com.zhengqing.wxmp.model.vo.WxMpUserDetailVO;
import com.zhengqing.wxmp.model.vo.WxMpUserPageVO;
import com.zhengqing.wxmp.service.IWxMpUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 微信公众号-用户 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxMpUserServiceImpl extends ServiceImpl<WxMpUserMapper, WxMpUser> implements IWxMpUserService {

    private final WxMpUserMapper wxMpUserMapper;

    @Override
    public IPage<WxMpUserPageVO> page(WxMpUserPageDTO params) {
        IPage<WxMpUserPageVO> result = this.wxMpUserMapper.selectDataPage(new Page<>(), params);
        List<WxMpUserPageVO> list = result.getRecords();
        list.forEach(WxMpUserPageVO::handleData);
        return result;
    }

    @Override
    public WxMpUserDetailVO detail(WxMpUserDetailDTO params) {
        WxMpUserDetailVO detailData = this.wxMpUserMapper.detail(params);
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WxMpUserSaveDTO params) {
        Integer id = params.getId();
        String appId = params.getAppId();
        String openId = params.getOpenId();
        String nickname = params.getNickname();
        String headImgUrl = params.getHeadImgUrl();

        WxMpUser.builder()
                .id(id)
                .appId(appId)
                .openId(openId)
                .nickname(nickname)
                .headImgUrl(headImgUrl)
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.wxMpUserMapper.deleteById(id);
    }

}
