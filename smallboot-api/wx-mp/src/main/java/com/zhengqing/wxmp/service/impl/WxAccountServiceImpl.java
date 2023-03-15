package com.zhengqing.wxmp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.wxmp.entity.WxAccount;
import com.zhengqing.wxmp.mapper.WxAccountMapper;
import com.zhengqing.wxmp.model.dto.WxAccountPageDTO;
import com.zhengqing.wxmp.model.dto.WxAccountSaveDTO;
import com.zhengqing.wxmp.model.vo.WxAccountPageVO;
import com.zhengqing.wxmp.service.IWxAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 微信公众号-账号 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxAccountServiceImpl extends ServiceImpl<WxAccountMapper, WxAccount> implements IWxAccountService {

    private final WxAccountMapper wxAccountMapper;

    @Override
    public IPage<WxAccountPageVO> page(WxAccountPageDTO params) {
        IPage<WxAccountPageVO> result = this.wxAccountMapper.selectDataPage(new Page<>(), params);
        List<WxAccountPageVO> list = result.getRecords();
        list.forEach(WxAccountPageVO::handleData);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WxAccountSaveDTO params) {
        Integer id = params.getId();
        String name = params.getName();
        Byte type = params.getType();
        String account = params.getAccount();
        String appId = params.getAppId();
        String appSecret = params.getAppSecret();
        String url = params.getUrl();
        String token = params.getToken();
        String aesKey = params.getAesKey();
        String qrCodeUrl = params.getQrCodeUrl();

        WxAccount.builder()
                .id(id)
                .name(name)
                .type(type)
                .account(account)
                .appId(appId)
                .appSecret(appSecret)
                .url(url)
                .token(token)
                .aesKey(aesKey)
                .qrCodeUrl(qrCodeUrl)
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.wxAccountMapper.deleteById(id);
    }

}
