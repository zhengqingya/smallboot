package com.zhengqing.wxmp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.wxmp.entity.WxMpAccount;
import com.zhengqing.wxmp.mapper.WxMpAccountMapper;
import com.zhengqing.wxmp.model.dto.WxMpAccountListDTO;
import com.zhengqing.wxmp.model.dto.WxMpAccountPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpAccountSaveDTO;
import com.zhengqing.wxmp.model.vo.WxMpAccountListVO;
import com.zhengqing.wxmp.model.vo.WxMpAccountPageVO;
import com.zhengqing.wxmp.service.IWxMpAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
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
public class WxMpAccountServiceImpl extends ServiceImpl<WxMpAccountMapper, WxMpAccount> implements IWxMpAccountService {

    private final WxMpAccountMapper wxMpAccountMapper;
    private final WxMpService wxMpService;

    @Override
    public IPage<WxMpAccountPageVO> page(WxMpAccountPageDTO params) {
        IPage<WxMpAccountPageVO> result = this.wxMpAccountMapper.selectDataPage(new Page<>(), params);
        List<WxMpAccountPageVO> list = result.getRecords();
        list.forEach(WxMpAccountPageVO::handleData);
        return result;
    }

    @Override
    public List<WxMpAccountListVO> list(WxMpAccountListDTO params) {
        return this.wxMpAccountMapper.selectDataList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WxMpAccountSaveDTO params) {
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

        WxMpAccount wxMpAccount = WxMpAccount.builder()
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
                .build();
        wxMpAccount.insertOrUpdate();

        this.wxMpService.addConfigStorage(appId, wxMpAccount.toWxMpConfigStorage());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        WxMpAccount wxMpAccount = this.wxMpAccountMapper.selectById(id);

        this.wxMpService.removeConfigStorage(wxMpAccount.getAppId());

        this.wxMpAccountMapper.deleteById(id);
    }

    @Override
    public void initWxMpConfigStorages() {
        List<WxMpAccount> list = this.list();

        list.forEach(wxMpAccount -> this.wxMpService.addConfigStorage(wxMpAccount.getAppId(), wxMpAccount.toWxMpConfigStorage()));

        log.info("初始化公众号配置成功!");
    }

}
