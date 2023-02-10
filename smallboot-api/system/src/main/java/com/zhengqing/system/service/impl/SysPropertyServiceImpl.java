package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.context.SysUserContext;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.system.constant.SystemConstant;
import com.zhengqing.system.entity.SysProperty;
import com.zhengqing.system.mapper.SysPropertyMapper;
import com.zhengqing.system.model.dto.SysPropertySaveDTO;
import com.zhengqing.system.model.vo.SysPropertyVO;
import com.zhengqing.system.service.ISysPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 系统管理-系统属性 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
@Slf4j
@Service
public class SysPropertyServiceImpl extends ServiceImpl<SysPropertyMapper, SysProperty> implements ISysPropertyService {

    @Resource
    private SysPropertyMapper sysPropertyMapper;

    @Override
    public Map<String, SysPropertyVO> mapByKey(List<String> keyList) {
        this.checkKey(keyList);
        Map<String, SysPropertyVO> dataMap = Maps.newHashMap();
        List<SysPropertyVO> dataList = this.listByKey(keyList);
        for (SysPropertyVO item : dataList) {
            dataMap.put(item.getKey(), item);
        }
        return dataMap;
    }

    @Override
    public List<SysPropertyVO> listByKey(List<String> keyList) {
        List<SysPropertyVO> dataList = this.listFromCacheByKey(keyList);
        if (CollectionUtils.isEmpty(dataList)) {
            log.warn("[系统管理] 系统属性缓存丢失，请检查：{}", keyList);
            // 如果缓存数据为空，则从db获取
            return this.listFromDbByKey(keyList);
        }
        return dataList;
    }

    @Override
    public List<SysPropertyVO> listFromDbByKey(List<String> keyList) {
        this.checkKey(keyList);
        return this.sysPropertyMapper.selectDataListByKey(keyList);
    }

    @Override
    public List<SysPropertyVO> listFromCacheByKey(List<String> keyList) {
        this.checkKey(keyList);
        List<SysPropertyVO> dataList = Lists.newLinkedList();
        keyList.forEach(keyItem -> {
            String dataJsonStr = RedisUtil.get(SystemConstant.CACHE_SYS_PROPERTY_PREFIX + keyItem);
            if (StringUtils.isNotBlank(dataJsonStr)) {
                dataList.add(JSONObject.parseObject(dataJsonStr, SysPropertyVO.class));
            }
        });
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
        Assert.isTrue(CollectionUtils.isEmpty(repeatKeyDataList), "属性key重复，请重新输入！");
    }

    @Override
    public SysProperty detail(Integer id) {
        SysProperty detailData = this.sysPropertyMapper.selectById(id);
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    public SysProperty detailByKey(String key) {
        Assert.notBlank(key, "属性不能为空!");
        SysProperty detailData = this.sysPropertyMapper.selectOne(
                new LambdaQueryWrapper<SysProperty>()
                        .eq(SysProperty::getKey, key)
                        .last(MybatisConstant.LIMIT_ONE));
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(ValidList<SysPropertySaveDTO> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        List<String> keyList = dataList.stream().map(SysPropertySaveDTO::getKey).collect(Collectors.toList());
        this.checkKey(keyList);
        dataList.forEach(item -> {
            item.setId(null);
            if (StringUtils.isBlank(item.getRemark())) {
                item.setRemark("");
            }
            item.setCurrentUserId(SysUserContext.getUserId());
        });
        // 删除旧数据
        this.sysPropertyMapper.deleteByKeyList(keyList);
        // 保存新数据
        this.sysPropertyMapper.batchInsertOrUpdate(dataList);
        // 更新redis缓存
        this.updateCache(keyList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByKey(String key) {
        Assert.notBlank(key, "属性不能为空!");
        this.sysPropertyMapper.deleteByKey(key);
        String redisKey = SystemConstant.CACHE_SYS_PROPERTY_PREFIX + key;
        log.info("[系统管理] 删除系统属性缓存：[{}]", redisKey);
        RedisUtil.delete(redisKey);
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
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        List<SysPropertyVO> dataList = this.listFromDbByKey(keyList);
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        dataList.forEach(item -> {
            String key = SystemConstant.CACHE_SYS_PROPERTY_PREFIX + item.getKey();
            // 加入||更新 缓存
            if (RedisUtil.hasKey(key)) {
                RedisUtil.delete(key);
                log.info("[系统管理] 系统属性[{}] 更新之前删除缓存" + key);
            }
            RedisUtil.set(key, JSON.toJSONString(item));
            log.info("[系统管理] 系统属性[{}] 加入缓存" + key);
        });
    }

}
