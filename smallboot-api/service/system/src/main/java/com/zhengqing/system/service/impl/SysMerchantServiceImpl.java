package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.system.entity.SysMerchant;
import com.zhengqing.system.enums.SysRoleCodeEnum;
import com.zhengqing.system.mapper.SysMerchantMapper;
import com.zhengqing.system.model.dto.SysMerchantListDTO;
import com.zhengqing.system.model.dto.SysMerchantPageDTO;
import com.zhengqing.system.model.dto.SysMerchantSaveDTO;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.vo.SysMerchantListVO;
import com.zhengqing.system.model.vo.SysMerchantPageVO;
import com.zhengqing.system.service.ISysMerchantService;
import com.zhengqing.system.service.ISysPermBusinessService;
import com.zhengqing.system.service.ISysRoleService;
import com.zhengqing.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 系统管理-商户管理 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMerchantServiceImpl extends ServiceImpl<SysMerchantMapper, SysMerchant> implements ISysMerchantService {

    private final SysMerchantMapper sysMerchantMapper;
    private final ISysPermBusinessService iSysPermBusinessService;
    private final ISysUserService iSysUserService;
    private final ISysRoleService iSysRoleService;

    @Override
    public IPage<SysMerchantPageVO> page(SysMerchantPageDTO params) {
        IPage<SysMerchantPageVO> result = this.sysMerchantMapper.selectDataPage(new Page<>(), params);
        List<SysMerchantPageVO> list = result.getRecords();
        list.forEach(SysMerchantPageVO::handleData);
        return result;
    }

    @Override
    public List<SysMerchantListVO> list(SysMerchantListDTO params) {
        return this.sysMerchantMapper.selectDataList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysMerchantSaveDTO params) {
        Integer id = params.getId();
        boolean isAdd = id == null;
        String name = params.getName();
        Integer customId = params.getCustomId();

        // 校验名称是否重复
        SysMerchant oldData = this.sysMerchantMapper.selectOne(new LambdaQueryWrapper<SysMerchant>().eq(SysMerchant::getName, name).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(oldData == null || oldData.getId().equals(id), "名称重复，请重新输入！");

        SysMerchant sysMerchant = SysMerchant.builder()
                .id(id)
                .name(name)
                .sort(params.getSort())
                .phone(params.getPhone())
                .email(params.getEmail())
                .status(params.getStatus())
                .remark(params.getRemark())
                .type(params.getType())
                .expireTime(params.getExpireTime())
                .userNum(params.getUserNum())
                .jobNum(params.getJobNum())
                .build();
        if (isAdd && customId != null) {
            sysMerchant.setId(customId);
        }
        sysMerchant.insertOrUpdate();

        if (isAdd) {
            // 创建商户关联的用户 & 分配角色权限
            // 查询商户管理员角色id
            Integer roleId = this.iSysRoleService.getRoleIdByCode(SysRoleCodeEnum.商户管理员);

            // 创建用户
            Integer userId = this.iSysUserService.addOrUpdateData(SysUserSaveDTO.builder()
                    .username(params.getUsername())
                    .nickname(params.getName())
                    .password(params.getPassword())
                    .phone(params.getPhone())
                    .roleIdList(Lists.newArrayList(roleId))
                    .isFixed(true)
                    .merchantId(sysMerchant.getId())
                    .build());
            sysMerchant.setAdminUserId(userId);
            sysMerchant.updateById();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.sysMerchantMapper.deleteById(id);
    }

}