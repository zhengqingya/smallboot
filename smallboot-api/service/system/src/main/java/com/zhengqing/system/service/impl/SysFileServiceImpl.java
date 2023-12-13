package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.common.db.util.TenantUtil;
import com.zhengqing.common.file.util.FileStorageUtil;
import com.zhengqing.system.entity.SysFile;
import com.zhengqing.system.mapper.SysFileMapper;
import com.zhengqing.system.model.dto.SysFilePageDTO;
import com.zhengqing.system.model.vo.SysFilePageVO;
import com.zhengqing.system.model.vo.SysFileVO;
import com.zhengqing.system.service.ISysFileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p> 系统管理-文件上传记录 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/28 11:24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

    private final SysFileMapper sysFileMapper;
    private final FileStorageUtil fileStorageUtil;
    @Value("${spring.profiles.active:dev}")
    private String env;

    @Override
    public IPage<SysFilePageVO> page(SysFilePageDTO params) {
        IPage<SysFilePageVO> result = this.sysFileMapper.selectDataPage(new Page<>(), params);
        List<SysFilePageVO> list = result.getRecords();
        list.forEach(SysFilePageVO::handleData);
        return result;
    }

    @Override
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public SysFileVO upload(MultipartFile file) {
        long fileSize = file.getSize();
        Assert.isTrue(fileSize < 1024 * 1024 * 5, "文件上传限制：最大5M！");
        String filename = file.getOriginalFilename();
        String fileType = file.getContentType();
        String md5 = DigestUtil.md5Hex(file.getBytes());

        SysFileVO result = SysFileVO.builder().name(filename).type(fileType).build();

        SysFile sysFile = TenantUtil.executeRemoveFlag(() -> this.sysFileMapper.selectOne(
                new LambdaQueryWrapper<SysFile>().eq(SysFile::getEnv, this.env).eq(SysFile::getMd5, md5).last(MybatisConstant.LIMIT_ONE)
        ));
        if (sysFile == null) {
            String fileUrl = this.fileStorageUtil.upload(file);
            this.sysFileMapper.insert(SysFile.builder()
                    .name(filename)
                    .url(fileUrl)
                    .type(fileType)
                    .md5(md5)
                    .size(fileSize)
                    .env(this.env)
                    .build());
            result.setUrl(fileUrl);
        } else {
            result.setUrl(sysFile.getUrl());
        }

        return result;
    }

}
