package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.system.constant.SystemConstant;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.mapper.SysDictTypeMapper;
import com.zhengqing.system.model.bo.SysDictTypeBO;
import com.zhengqing.system.model.dto.SysDictTypeSaveDTO;
import com.zhengqing.system.model.vo.SysDictTypeListVO;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysDictTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典类型-服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Lazy
    @Resource
    private ISysDictService iSysDictService;

    private final SysDictTypeMapper sysDictTypeMapper;

    @Override
    public List<SysDictTypeListVO> listByOpen() {
        return this.sysDictTypeMapper.selectDictTypeListByOpen();
    }

    @Override
    public Map<String, Integer> getDictTypeIdMap(List<String> codeList) {
        if (CollectionUtils.isEmpty(codeList)) {
            return Maps.newHashMap();
        }
        List<SysDictTypeBO> dictTypeList = this.sysDictTypeMapper.selectDataList(codeList);
        return dictTypeList.stream().collect(Collectors.toMap(SysDictTypeBO::getCode, SysDictTypeBO::getId, (k1, k2) -> k1));
    }

    @Override
    public SysDictType detail(Integer dictTypeId) {
        SysDictType sysDictType = this.sysDictTypeMapper.selectById(dictTypeId);
        Assert.notNull(sysDictType, "字典类型不存在！");
        return sysDictType;
    }

    @Override
    public SysDictType detailByCode(String code) {
        SysDictType sysDictType = this.sysDictTypeMapper.selectOne(new LambdaQueryWrapper<SysDictType>()
                .eq(SysDictType::getCode, code)
                .last(MybatisConstant.LIMIT_ONE));
        Assert.notNull(sysDictType, "字典类型不存在！");
        return sysDictType;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysDictTypeSaveDTO params) {
        Integer id = params.getId();
        String code = params.getCode();
        String name = params.getName();
        Integer status = params.getStatus();

        // 校验编码是否重复
        SysDictType sysDictTypeOld = this.sysDictTypeMapper.selectOne(new LambdaQueryWrapper<SysDictType>()
                .eq(SysDictType::getCode, code)
                .last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysDictTypeOld == null || sysDictTypeOld.getId().equals(id), "字典类型编码重复，请重新输入！");

        // 保存数据
        SysDictType sysDictType = SysDictType.builder()
                .id(id)
                .name(name)
                .status(status)
                .build();
        if (params.getId() == null) {
            sysDictType.setCode(code);
            this.sysDictTypeMapper.insert(sysDictType);
        } else {
            // 校验该数据是否存在
            SysDictType detail = this.detail(id);
            sysDictType.setCode(detail.getCode());
            this.sysDictTypeMapper.updateById(sysDictType);
        }

        // 更新缓存
        this.iSysDictService.updateCache(Collections.singletonList(sysDictType.getCode()));
        return sysDictType.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteType(Integer id) {
        SysDictType sysDictType = this.sysDictTypeMapper.selectById(id);
        String code = sysDictType.getCode();
        // 1、 先删除数据字典
        this.iSysDictService.deleteDictByCode(code);
        // 2、 再删除数据字典类型
        this.sysDictTypeMapper.deleteById(id);
        // 3、 最后删除缓存
        RedisUtil.delete(SystemConstant.CACHE_SYS_DICT_PREFIX + code);
        log.info("删除数据字典[{}] & 删除缓存成功", code);
    }

}
