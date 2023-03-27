package com.zhengqing.wxmp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.util.BigDecimalUtil;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.wxmp.entity.WxMpUser;
import com.zhengqing.wxmp.mapper.WxMpUserMapper;
import com.zhengqing.wxmp.model.dto.WxMpUserPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpUserSaveDTO;
import com.zhengqing.wxmp.model.vo.WxMpUserPageVO;
import com.zhengqing.wxmp.service.IWxMpUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.apache.commons.lang3.StringUtils;
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
    private final WxMpService wxMpService;

    @Override
    public IPage<WxMpUserPageVO> page(WxMpUserPageDTO params) {
        IPage<WxMpUserPageVO> result = this.wxMpUserMapper.selectDataPage(new Page<>(), params);
        List<WxMpUserPageVO> list = result.getRecords();
        list.forEach(WxMpUserPageVO::handleData);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WxMpUserSaveDTO params) {
        Integer id = params.getId();
        String appId = params.getAppId();
        String openid = params.getOpenid();
        String nickname = params.getNickname();
        String headImgUrl = params.getHeadImgUrl();

        WxMpUser.builder()
                .id(id)
                .appId(appId)
                .openid(openid)
                .nickname(nickname)
                .headImgUrl(headImgUrl)
                .build()
                .insertOrUpdate();
    }

    @Override
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public void sync(String appId) {
        // 1、删除旧数据
        this.wxMpUserMapper.delete(
                new LambdaQueryWrapper<WxMpUser>().eq(WxMpUser::getAppId, appId)
        );

        // 2、切换公众号
        this.wxMpService.switchoverTo(appId);

        // 3、查询用户数据 & 保存
        WxMpUserService wxMpUserService = this.wxMpService.getUserService();
        String nextOpenid = null;
        boolean isGo = true;
        while (isGo) {
            // 一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
            WxMpUserList wxMpUserList = wxMpUserService.userList(nextOpenid);

            List<String> openidList = wxMpUserList.getOpenids();
            // 保存用户数据
            this.saveWxMpUser(openidList, appId);

            // 下一次数据拉取
            nextOpenid = wxMpUserList.getNextOpenid();
            // 是否继续循环
            isGo = StringUtils.isNotBlank(nextOpenid) && wxMpUserList.getCount() >= 10000;
        }
    }

    /**
     * 保存用户数据
     *
     * @param openidList openid集合
     * @param appId      公众号appId
     * @return void
     * @author zhengqingya
     * @date 2023/3/20 15:15
     */
    @SneakyThrows(Exception.class)
    private void saveWxMpUser(List<String> openidList, String appId) {
        int sum = openidList.size();
        int page = BigDecimalUtil.divide(sum, 100);
        for (int i = 0; i < page; i++) {
            List<WxMpUser> saveList = Lists.newArrayList();
            List<String> subOpenidList = openidList.subList(i, i + 100 <= sum ? i + 100 : sum);
            List<me.chanjar.weixin.mp.bean.result.WxMpUser> userList = this.wxMpService.getUserService().userInfoList(subOpenidList);
            userList.forEach(item -> saveList.add(
                    WxMpUser.builder()
                            .appId(appId)
                            .openid(item.getOpenId())
                            .nickname(item.getNickname())
                            .headImgUrl(item.getHeadImgUrl())
                            .subscribeScene(item.getSubscribeScene())
                            .subscribeTime(MyDateUtil.longToDate(item.getSubscribeTime()))
                            .build()
            ));
            this.saveBatch(saveList);
        }
    }

}
