package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.enums.ApiResultCodeEnum;
import com.zhengqing.common.base.enums.CommonStatusEnum;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.system.entity.SysAppConfig;
import com.zhengqing.system.entity.SysDept;
import com.zhengqing.system.mapper.SysDeptMapper;
import com.zhengqing.system.model.bo.SysAppConfigBO;
import com.zhengqing.system.model.dto.SysDeptSaveDTO;
import com.zhengqing.system.model.dto.SysDeptTreeDTO;
import com.zhengqing.system.model.vo.SysDeptCheckVO;
import com.zhengqing.system.model.vo.SysDeptTreeVO;
import com.zhengqing.system.service.ISysAppConfigService;
import com.zhengqing.system.service.ISysDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p> 系统管理-部门 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 18:10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    private final SysDeptMapper sysDeptMapper;
    private final ISysAppConfigService iSysAppConfigService;

    @Override
    public SysDept detail(Integer id) {
        Assert.notNull(id, "部门不存在！");
        SysDept sysDept = this.sysDeptMapper.selectById(id);
        Assert.notNull(sysDept, "部门不存在！");
        return sysDept;
    }

    @Override
    public List<SysDeptTreeVO> tree(SysDeptTreeDTO params) {
        List<SysDeptTreeVO> list = this.sysDeptMapper.selectDataList(params);
        if (CollUtil.isEmpty(list)) {
            return Lists.newArrayList();
        }
        List<Integer> appConfigIdList = list.stream().map(SysDeptTreeVO::getAppConfigId).collect(Collectors.toList());
        Map<Integer, SysAppConfigBO> appConfigMap = this.iSysAppConfigService.mapByIdList(appConfigIdList);
        list.forEach(e -> e.setAppConfigObj(appConfigMap.get(e.getAppConfigId())));
        if (StrUtil.isNotBlank(params.getName())) {
            return list;
        }
        return this.recurveDept(AppConstant.PARENT_ID, list, params.getExcludeDeptId());
    }

    /**
     * 递归部门
     *
     * @param parentId      父id
     * @param allList       所有部门
     * @param excludeDeptId 排除指定部门id下级的数据
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<SysDeptTreeVO> recurveDept(Integer parentId, List<SysDeptTreeVO> allList, Integer excludeDeptId) {
        if (parentId.equals(excludeDeptId)) {
            return Lists.newArrayList();
        }
        // 存放子集合
        List<SysDeptTreeVO> childList = allList.stream().filter(e -> e.getParentId().equals(parentId)).collect(Collectors.toList());
        // 递归
        childList.forEach(item -> {
            item.setChildren(this.recurveDept(item.getId(), allList, excludeDeptId));
            item.handleData();
        });
        return childList;
    }

    @Override
    public SysDeptCheckVO checkData(Integer deptId) {
        SysDept sysDept = null;
        try {
            sysDept = this.detail(deptId);
            Assert.isTrue(Objects.equals(CommonStatusEnum.ENABLE.getStatus(), sysDept.getStatus()), "服务已停用！");
            Date expireTime = sysDept.getExpireTime();
            if (expireTime != null) {
                Assert.isTrue(expireTime.after(new Date()), "限制：服务已到期！过期时间：" + MyDateUtil.dateToStr(expireTime));
            }
        } catch (Exception e) {
            throw new MyException(e.getMessage(), ApiResultCodeEnum.APP_SERVICE_ERROR.getCode());
        }
        SysDeptCheckVO result = SysDeptCheckVO.builder()
                .id(sysDept.getId())
                .name(sysDept.getName())
                .status(sysDept.getStatus())
                .expireTime(sysDept.getExpireTime())
                .userNum(sysDept.getUserNum())
                .build();
        Integer appConfigId = sysDept.getAppConfigId();
        if (appConfigId != null) {
            SysAppConfig sysAppConfig = this.iSysAppConfigService.getById(appConfigId);
            result.setAppType(sysAppConfig.getAppType());
            result.setAppSecret(sysAppConfig.getAppSecret());
        }
        return result;
    }

    @Override
    public SysDeptCheckVO configByAppId(Integer appId) {
        SysAppConfig sysAppConfig = this.iSysAppConfigService.getById(appId);
        Assert.notNull(sysAppConfig, "小程序暂未配置，请联系系统管理员！");
        SysDept sysDept = this.sysDeptMapper.selectOne(new LambdaQueryWrapper<SysDept>().eq(SysDept::getAppConfigId, sysAppConfig.getId()).last(MybatisConstant.LIMIT_ONE));
        Assert.notNull(sysDept, "企业数据丢失，请联系系统管理员！");
        return this.checkData(sysDept.getId());
    }

    @Override
    public List<Integer> getChildDeptIdList(Integer deptId) {
        return this.recurveDeptId(deptId, this.list(), Lists.newArrayList());
    }

    /**
     * 递归部门
     *
     * @param parentId   父id
     * @param allList    所有部门
     * @param deptIdList 最终结果
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<Integer> recurveDeptId(Integer parentId, List<SysDept> allList, List<Integer> deptIdList) {
        deptIdList.add(parentId);
        List<SysDept> childList = allList.stream().filter(e -> e.getParentId().equals(parentId)).collect(Collectors.toList());
        if (CollUtil.isEmpty(childList)) {
            return deptIdList;
        }
        for (SysDept item : childList) {
            this.recurveDeptId(item.getId(), allList, deptIdList);
        }
        return deptIdList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysDeptSaveDTO params) {
        Integer id = params.getId();
        String name = params.getName();
        // 校验名称是否重复
        SysDept sysDeptOld = this.sysDeptMapper.selectOne(new LambdaQueryWrapper<SysDept>().eq(SysDept::getName, name).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysDeptOld == null || sysDeptOld.getId().equals(id), "名称重复，请重新输入！");

        // 构建保存参数
        SysDept sysDept = SysDept.builder()
                .id(id)
                .parentId(params.getParentId())
                .name(name)
                .sort(params.getSort())
                .leaderUserId(params.getLeaderUserId())
                .phone(params.getPhone())
                .email(params.getEmail())
                .status(params.getStatus())
                .provinceName(params.getProvinceName())
                .cityName(params.getCityName())
                .areaName(params.getAreaName())
                .address(params.getAddress())
                .remark(params.getRemark())
                .expireTime(params.getExpireTime())
                .userNum(params.getUserNum())
                .jobNum(params.getJobNum())
                .build();

        // 保存小程序配置
        Integer appConfigId = this.iSysAppConfigService.addOrUpdateData(params.getAppConfigObj());
        sysDept.setAppConfigId(appConfigId);
        sysDept.insertOrUpdate();
        return sysDept.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        List<SysDeptTreeVO> list = this.tree(SysDeptTreeDTO.builder().parentId(id).build());
        Assert.isTrue(CollUtil.isEmpty(list), "请先删除子部门后再删除当前部门！");
        this.sysDeptMapper.deleteById(id);
    }


}
