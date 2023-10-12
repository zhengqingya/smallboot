package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.context.SysUserContext;
import com.zhengqing.common.base.enums.YesNoEnum;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.common.web.util.MyValidatorUtil;
import com.zhengqing.system.constant.SystemConstant;
import com.zhengqing.system.entity.SysDict;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.mapper.SysDictMapper;
import com.zhengqing.system.model.dto.SysDictSaveBatchDTO;
import com.zhengqing.system.model.dto.SysDictSaveDTO;
import com.zhengqing.system.model.dto.SysDictTypeSaveDTO;
import com.zhengqing.system.model.vo.SysDictTypeListVO;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.processor.ElementIconPageProcessor;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysDictTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典-服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:51
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    private final SysDictMapper sysDictMapper;

    @Lazy
    @Resource
    private ISysDictTypeService iSysDictTypeService;

    @Override
    public List<SysDictVO> listByCode(String code) {
        return this.sysDictMapper.selectDictListByCode(null, Lists.newArrayList(code));
    }

    @Override
    public Map<String, List<SysDictVO>> listByOpenCode(List<String> codeList) {
        Map<String, List<SysDictVO>> dictDataMap = this.listFromCacheByCode(codeList);
        if (CollectionUtils.isEmpty(dictDataMap)) {
            log.warn("[系统管理] 数据字典缓存丢失，请检查：{}", codeList);
            // 如果缓存数据为空，则从db获取
            return this.listFromDbByOpenCode(codeList);
        }
        return dictDataMap;
    }

    @Override
    public Map<String, List<SysDictVO>> listFromDbByOpenCode(List<String> codeList) {
        this.checkKey(codeList);
        Map<String, List<SysDictVO>> dictDataMap = Maps.newHashMap();
        List<SysDictVO> dictDataList = this.sysDictMapper.selectDictListByCode(YesNoEnum.YES.getValue(), codeList);
        for (SysDictVO dictItem : dictDataList) {
            dictDataMap.computeIfAbsent(dictItem.getCode(), k -> new LinkedList<>()).add(dictItem);
        }
        // 计算有没有差集，若有则装入空数据，返回
//        List<String> codeListByDb = dictDataList.stream().map(SysDictVO::getCode).collect(Collectors.toList());
//        List<String> newCodeList = codeList.stream().filter(code -> !codeListByDb.contains(code)).collect(Collectors.toList());
//        newCodeList.forEach(code -> dictDataMap.put(code, Lists.newArrayList()));
        return dictDataMap;
    }

    @Override
    public Map<String, List<SysDictVO>> listFromCacheByCode(List<String> codeList) {
        this.checkKey(codeList);
        Map<String, List<SysDictVO>> dictDataMap = Maps.newHashMap();
        codeList.forEach(codeItem -> {
            String dictJsonStr = RedisUtil.get(SystemConstant.CACHE_SYS_DICT_PREFIX + codeItem);
            if (StringUtils.isBlank(dictJsonStr)) {
//                dictDataMap.put(codeItem, Lists.newArrayList());
            } else {
                dictDataMap.put(codeItem, JSONArray.parseArray(dictJsonStr, SysDictVO.class));
            }
        });
        return dictDataMap;
    }

    @Override
    public Map<Integer, SysDict> map(List<Integer> idList) {
        Map<Integer, SysDict> resultMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(idList)) {
            return resultMap;
        }
        List<SysDict> sysDictList = this.sysDictMapper.selectBatchIds(idList);
        return sysDictList.stream().collect(Collectors.toMap(SysDict::getId, e -> e));
    }

    /**
     * 字典code值校验
     *
     * @param codeList 属性key
     * @return void
     * @author zhengqingya
     * @date 2021/9/7 10:00
     */
    private void checkKey(List<String> codeList) {
        Assert.notNull(codeList, "字典code不能为空！");
        codeList.forEach(codeItem -> Assert.notBlank(codeItem, "字典code不能为空！"));
    }

    @Override
    public SysDict detail(Integer dictId) {
        SysDict sysDict = this.sysDictMapper.selectById(dictId);
        Assert.notNull(sysDict, "字典不存在！");
        return sysDict;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysDictSaveDTO params) {
        String code = params.getCode();
        SysDictType dictTypeData = this.iSysDictTypeService.detailByCode(code);
        Integer id = params.getId();
        String name = params.getName();
        String value = params.getValue();
        Integer sort = params.getSort();
        String remark = params.getRemark();

        // 校验名称是否重复
        SysDict sysDictOldByName = this.sysDictMapper.selectOne(new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getCode, code)
                .eq(SysDict::getName, name)
                .last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysDictOldByName == null || sysDictOldByName.getId().equals(id), "字典名称重复，请重新输入！");

        // 校验Value是否重复
        SysDict sysDictOldByValue = this.sysDictMapper.selectOne(new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getCode, code)
                .eq(SysDict::getValue, value)
                .last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysDictOldByValue == null || sysDictOldByValue.getId().equals(id), "字典名称值重复，请重新输入！");

        // 保存数据
        SysDict sysDict = SysDict.builder()
                .id(id)
                .dictTypeId(dictTypeData.getId())
                .code(code)
                .name(name)
                .value(value)
                .status(YesNoEnum.YES.getValue())
                .sort(sort)
                .remark(remark)
                .build();
        if (params.getId() == null) {
            this.sysDictMapper.insert(sysDict);
        } else {
            // 校验该数据是否存在
            this.detail(id);
            this.sysDictMapper.updateById(sysDict);
        }
        // 更新缓存
        this.updateCache(Collections.singletonList(code));
        return sysDict.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateBatch(Map<String, ValidList<SysDictSaveBatchDTO>> dictDataMap, Boolean isAddForNotExist) {
        if (CollectionUtils.isEmpty(dictDataMap)) {
            return;
        }
        List<SysDictSaveBatchDTO> saveList = Lists.newArrayList();
        List<String> codeList = Lists.newLinkedList();
        dictDataMap.forEach((code, dictListItem) -> codeList.add(code));
        this.checkKey(codeList);
        Map<String, Integer> dictTypeIdMap = this.iSysDictTypeService.getDictTypeIdMap(codeList);
        dictDataMap.forEach((code, dictListItem) -> {
            MyValidatorUtil.validate(dictListItem);
            Integer dictTypeId = dictTypeIdMap.get(code);
            if (dictTypeId == null) {
                if (isAddForNotExist) {
                    // 新增数据
                    String dictTypeName = dictListItem.get(0).getDictTypeName();
                    Assert.notBlank(dictTypeName, "新增字典类型数据时，字典类型名称值不能为空！");
                    dictTypeId = this.iSysDictTypeService.addOrUpdateData(SysDictTypeSaveDTO.builder()
                            .code(code)
                            .name(dictTypeName)
                            .status(YesNoEnum.YES.getValue())
                            .build());
                } else {
                    Assert.notNull(dictTypeId, String.format("数据字典[%s]丢失或未启用，请联系系统管理员!", code));
                }
            }

            // 删除该code关联字典
            this.sysDictMapper.deleteByCode(code);

            // 校验字典value和名称是否重复
            List<String> repeatValueDataList = dictListItem
                    .stream().map(SysDictSaveBatchDTO::getValue).collect(Collectors.toList())
                    .stream().collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            Assert.isTrue(CollectionUtils.isEmpty(repeatValueDataList), "字典名称值重复，请重新输入！");
            List<String> repeatNameDataList = dictListItem
                    .stream().map(SysDictSaveBatchDTO::getName).collect(Collectors.toList())
                    .stream().collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            Assert.isTrue(CollectionUtils.isEmpty(repeatNameDataList), "字典名称重复，请重新输入！");

            for (SysDictSaveBatchDTO item : dictListItem) {
                item.setId(null);
                item.setDictTypeId(dictTypeId);
                item.setCode(code);
                item.setCurrentUserId(Long.valueOf(SysUserContext.getUserId()));
                if (item.getStatus() == null) {
                    item.setStatus(YesNoEnum.YES.getValue());
                }
            }
            saveList.addAll(dictListItem);
        });
        // 保存数据
        if (!CollectionUtils.isEmpty(saveList)) {
            this.sysDictMapper.batchInsertOrUpdate(saveList);
        }
        // 更新缓存
        this.updateCache(codeList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictById(Integer id) {
        SysDict sysDict = this.sysDictMapper.selectById(id);
        if (sysDict == null) {
            return;
        }
        this.sysDictMapper.deleteById(id);
        // 更新缓存
        this.updateCache(Collections.singletonList(sysDict.getCode()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictByCode(String code) {
        Assert.notBlank(code, "字典code不能为空!");
        this.sysDictMapper.deleteByCode(code);
    }

    @Override
    public void updateCache(List<String> codeList) {
        if (CollectionUtils.isEmpty(codeList)) {
            return;
        }
        Map<String, List<SysDictVO>> dictDataMap = this.listFromDbByOpenCode(codeList);
        dictDataMap.forEach((code, dictList) -> {
            String key = SystemConstant.CACHE_SYS_DICT_PREFIX + code;
            RedisUtil.set(key, JSON.toJSONString(dictList));
            log.info("数据字典[{}] 更新缓存" + key);
        });
    }

    @Override
    public void initCache() {
        List<SysDictTypeListVO> sysDictTypeList = this.iSysDictTypeService.listByOpen();
        if (!CollectionUtils.isEmpty(sysDictTypeList)) {
            List<String> codeList = sysDictTypeList.stream().map(SysDictTypeListVO::getCode).collect(Collectors.toList());
            Map<String, List<SysDictVO>> dictDataMap = this.listFromDbByOpenCode(codeList);
            dictDataMap.forEach((code, dictDataList) -> RedisUtil.set(SystemConstant.CACHE_SYS_DICT_PREFIX + code, JSON.toJSONString(dictDataList)));
        }
        log.info("初始化数据字典缓存成功!");
    }

    @Override
    public void initElIconData() {
        Spider.create(new ElementIconPageProcessor(this))
                // 从指定的url地址开始抓
                .addUrl("https://element-plus.org/zh-CN/component/icon.html#icon-collection")
                // 开启5个线程抓取
                .thread(5)
                // 启动爬虫
                .run();
    }

}
