package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.context.SysUserContext;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.system.constant.SystemConstant;
import com.zhengqing.system.entity.SysConfig;
import com.zhengqing.system.mapper.SysConfigMapper;
import com.zhengqing.system.model.dto.SysConfigPageDTO;
import com.zhengqing.system.model.dto.SysConfigSaveDTO;
import com.zhengqing.system.model.vo.SysConfigPageVO;
import com.zhengqing.system.model.vo.SysConfigVO;
import com.zhengqing.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 系统管理-系统配置 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    private final SysConfigMapper sysConfigMapper;

    @Override
    public void initCache() {
        List<SysConfig> list = this.sysConfigMapper.selectList(null);
        if (CollUtil.isEmpty(list)) {
            return;
        }
        list.forEach(item -> {
            String key = SystemConstant.CACHE_SYS_CONFIG_PREFIX + item.getKey();
            RedisUtil.set(key, JSON.toJSONString(item));
        });
        log.info("初始化系统配置缓存成功!");
    }

    /**
     * 更新redis缓存
     *
     * @param keyList 属性key
     * @return void
     * @author zhengqingya
     * @date 2021/9/7 11:21
     */
    private void updateCache(List<String> keyList) {
        List<SysConfigVO> dataList = this.listFromDbByKey(keyList);
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        dataList.forEach(item -> {
            String key = SystemConstant.CACHE_SYS_CONFIG_PREFIX + item.getKey();
            RedisUtil.set(key, JSON.toJSONString(item));
            log.info("[系统管理] 系统配置[{}] 加入缓存" + key);
        });
    }

    @Override
    public IPage<SysConfigPageVO> listPage(SysConfigPageDTO params) {
        return this.sysConfigMapper.selectListPage(new Page<>(), params);
    }

    @Override
    public Map<String, SysConfigVO> mapByKey(List<String> keyList) {
        this.checkKey(keyList);
        Map<String, SysConfigVO> dataMap = Maps.newHashMap();
        List<SysConfigVO> dataList = this.listByKey(keyList);
        for (SysConfigVO item : dataList) {
            dataMap.put(item.getKey(), item);
        }
        return dataMap;
    }

    @Override
    public List<SysConfigVO> listByKey(List<String> keyList) {
        List<SysConfigVO> dataList = this.listFromCacheByKey(keyList);
        if (CollUtil.isEmpty(dataList)) {
            log.warn("[系统管理] 系统配置缓存丢失，请检查：{}", keyList);
            // 如果缓存数据为空，则从db获取
            return this.listFromDbByKey(keyList);
        }
        return dataList;
    }

    @Override
    public List<SysConfigVO> listFromDbByKey(List<String> keyList) {
        if (CollUtil.isEmpty(keyList)) {
            return Lists.newArrayList();
        }
        this.checkKey(keyList);
        List<SysConfigVO> dataList = this.sysConfigMapper.selectDataListByKey(keyList);
        dataList.forEach(SysConfigVO::handleData);
        return dataList;
    }

    @Override
    public List<SysConfigVO> listFromCacheByKey(List<String> keyList) {
        this.checkKey(keyList);
        List<SysConfigVO> dataList = Lists.newLinkedList();
        keyList.forEach(keyItem -> {
            String dataJsonStr = RedisUtil.get(SystemConstant.CACHE_SYS_CONFIG_PREFIX + keyItem);
            if (StringUtils.isNotBlank(dataJsonStr)) {
                dataList.add(JSONUtil.toBean(dataJsonStr, SysConfigVO.class));
            }
        });
        dataList.forEach(SysConfigVO::handleData);
        return dataList;
    }

    /**
     * 属性key值校验
     *
     * @param keyList 属性key
     * @return void
     * @author zhengqingya
     * @date 2021/9/7 10:00
     */
    private void checkKey(List<String> keyList) {
        Assert.notNull(keyList, "属性key不能为空！");
        keyList.forEach(keyItem -> Assert.notBlank(keyItem, "属性key不能为空！"));
        // 校验属性key是否重复
        List<String> repeatKeyDataList = keyList
                .stream().collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        Assert.isTrue(CollUtil.isEmpty(repeatKeyDataList), "属性key重复，请重新输入！");
    }

    @Override
    public SysConfig detail(Integer id) {
        SysConfig detailData = this.sysConfigMapper.selectById(id);
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    public SysConfig detailByKey(String key) {
        Assert.notBlank(key, "属性不能为空!");
        SysConfig detailData = this.sysConfigMapper.selectOne(
                new LambdaQueryWrapper<SysConfig>()
                        .eq(SysConfig::getKey, key)
                        .last(MybatisConstant.LIMIT_ONE));
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysConfigSaveDTO params) {
        Integer id = params.getId();
        String key = params.getKey();

        // 校验key是否重复
        SysConfig sysConfigOld = this.sysConfigMapper.selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getKey, key).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysConfigOld == null || sysConfigOld.getId().equals(id), "key重复，请重新输入！");

        // 保存新数据
        this.sysConfigMapper.batchInsertOrUpdate(Lists.newArrayList(params));
        // 更新redis缓存
        this.updateCache(Lists.newArrayList(key));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(ValidList<SysConfigSaveDTO> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        List<String> keyList = dataList.stream().map(SysConfigSaveDTO::getKey).collect(Collectors.toList());
        this.checkKey(keyList);
        dataList.forEach(item -> {
            item.setId(null);
            if (StringUtils.isBlank(item.getRemark())) {
                item.setRemark("");
            }
            item.setCurrentUserId(SysUserContext.getUserId());
        });
        // 删除旧数据
        this.sysConfigMapper.deleteByKeyList(keyList);
        // 保存新数据
        this.sysConfigMapper.batchInsertOrUpdate(dataList);
        // 更新redis缓存
        this.updateCache(keyList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByKey(String key) {
        Assert.notBlank(key, "属性不能为空!");
        this.sysConfigMapper.deleteByKeyList(Lists.newArrayList(key));
        String redisKey = SystemConstant.CACHE_SYS_CONFIG_PREFIX + key;
        log.info("[系统管理] 删除系统配置缓存：[{}]", redisKey);
        RedisUtil.delete(redisKey);
    }

}
