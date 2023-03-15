package com.zhengqing.wxmp.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.wxmp.entity.WxUser;
import com.zhengqing.wxmp.mapper.WxUserMapper;
import com.zhengqing.wxmp.model.dto.WxUserDetailDTO;
import com.zhengqing.wxmp.model.dto.WxUserPageDTO;
import com.zhengqing.wxmp.model.dto.WxUserSaveDTO;
import com.zhengqing.wxmp.model.vo.WxUserDetailVO;
import com.zhengqing.wxmp.model.vo.WxUserPageVO;
import com.zhengqing.wxmp.service.IWxUserService;
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
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements IWxUserService {

    private final WxUserMapper wxUserMapper;

    @Override
    public IPage<WxUserPageVO> page(WxUserPageDTO params) {
        IPage<WxUserPageVO> result = this.wxUserMapper.selectDataPage(new Page<>(), params);
        List<WxUserPageVO> list = result.getRecords();
        list.forEach(WxUserPageVO::handleData);
        return result;
    }

    @Override
    public WxUserDetailVO detail(WxUserDetailDTO params) {
        WxUserDetailVO detailData = this.wxUserMapper.detail(params);
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WxUserSaveDTO params) {
        Integer id = params.getId();
        String appId = params.getAppId();
        String openId = params.getOpenId();
        String nickname = params.getNickname();
        String headImgUrl = params.getHeadImgUrl();

        WxUser.builder()
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
        this.wxUserMapper.deleteById(id);
    }

}
