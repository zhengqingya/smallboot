package com.zhengqing.mall.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.mall.entity.OmsLogistic;
import com.zhengqing.mall.mapper.OmsLogisticMapper;
import com.zhengqing.mall.model.dto.MallLogisticsDTO;
import com.zhengqing.mall.model.dto.OmsLogisticDTO;
import com.zhengqing.mall.model.enums.TpsLogisticsCodeEnum;
import com.zhengqing.mall.model.enums.TpsLogisticsStatusEnum;
import com.zhengqing.mall.model.vo.OmsLogisticVO;
import com.zhengqing.mall.model.vo.TpsLogisticsVO;
import com.zhengqing.mall.service.IOmsLogisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 商城-物流信息表 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/26 15:01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OmsLogisticServiceImpl extends ServiceImpl<OmsLogisticMapper, OmsLogistic> implements IOmsLogisticService {

    private final OmsLogisticMapper omsLogisticMapper;


    @Override
    public OmsLogisticVO detail(OmsLogisticDTO params) {
        String logisticsCode = params.getLogisticsCode();
        String logisticsNo = params.getLogisticsNo();
        OmsLogistic detailData = this.omsLogisticMapper.selectOne(
                new LambdaQueryWrapper<OmsLogistic>()
                        .eq(OmsLogistic::getLogisticsCode, logisticsCode)
                        .eq(OmsLogistic::getLogisticsNo, logisticsNo)
                        .last(MybatisConstant.LIMIT_ONE));
        Assert.notNull(detailData, "该物流数据不存在！");

        // 这里先从开放api中拉取物流数据，更新到库中，再返回给前端，如果后期第三方物流开放平台压力遭不住，将此处代码注释，使用定时任务方案
        TpsLogisticsVO logisticByRpc = this.getLogisticByRpc(MallLogisticsDTO.builder()
                .logisticsCode(logisticsCode)
                .logisticsNo(logisticsNo)
                .receiverPhone(detailData.getReceiverPhone())
                .build());
        detailData.setTraceJson(JSON.toJSONString(logisticByRpc.getTraceList()));
        detailData.setDelivererName(logisticByRpc.getDelivererName());
        detailData.setDelivererPhone(logisticByRpc.getDelivererPhone());
        detailData.setLocation(logisticByRpc.getLocation());
        detailData.setStatus(logisticByRpc.getStatus());
        detailData.setStatusEx(logisticByRpc.getStatusEx());
        detailData.updateById();

        // 封装响应结果
        Integer status = detailData.getStatus();
        Integer statusEx = detailData.getStatusEx();
        OmsLogisticVO result = OmsLogisticVO.builder()
                .logisticsCompany(detailData.getLogisticsCompany())
                .logisticsCode(detailData.getLogisticsCode())
                .logisticsNo(detailData.getLogisticsNo())
                .traceJson(detailData.getTraceJson())
                .delivererName(detailData.getDelivererName())
                .delivererPhone(detailData.getDelivererPhone())
                .location(detailData.getLocation())
                .status(status)
                .statusName(TpsLogisticsStatusEnum.getEnum(status).getDesc())
                .statusEx(statusEx)
                .statusExName(TpsLogisticsStatusEnum.getEnum(statusEx).getDesc())
                .build();
        result.handleData();
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLogistic(String logisticsCode, String logisticsNo, String receiverPhone) {
        OmsLogistic oldData = this.omsLogisticMapper.selectOne(
                new LambdaQueryWrapper<OmsLogistic>()
                        .eq(OmsLogistic::getLogisticsCode, logisticsCode)
                        .eq(OmsLogistic::getLogisticsNo, logisticsNo)
                        .last(MybatisConstant.LIMIT_ONE));
        if (oldData != null) {
            return;
        }
        this.addOrUpdateData(OmsLogistic.builder()
                .logisticsCompany(TpsLogisticsCodeEnum.getEnumByCode(logisticsCode).getName())
                .logisticsCode(logisticsCode)
                .logisticsNo(logisticsNo)
                .receiverPhone(receiverPhone)
                .build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrUpdateData(OmsLogistic omsLogistic) {
        String id = omsLogistic.getId();
        if (id == null) {
            // 新增
            id = IdGeneratorUtil.nextStrId();
            omsLogistic.setId(id);
            omsLogistic.insert();
        } else {
            // 更新
            omsLogistic.updateById();
        }
        return id;
    }

    @Override
    public void updateDb() {
        log.info("[商城] 更新db物流信息 现在时间：【{}】", DateTime.now());
        // 注：不能加事务 原因：如果加上事务，每次循环更新的数据，不能及时写入库中，会导致后面循环查询的数据有之前已更新过的数据，数据量大的时候，即会出现死循环问题！
        final Date updateTime = new Date();
        // 首页为1
        int page = 1;
        // 每页处理条数
        int pageSize = 100;
        // 更新数
        int total = 0;
        while (true) {
            TenantIdContext.removeFlag();
            log.info("[商城] 轮循更新db物流信息 page:{} pageSize:{} total:{}", page, pageSize, total);
            try {
                // 查询未签收的数据
                IPage<OmsLogistic> mysqlLogisticPage = this.omsLogisticMapper.selectUnFinishData(new Page<>(page, pageSize), updateTime);
                List<OmsLogistic> mysqlLogisticList = mysqlLogisticPage.getRecords();
                if (CollectionUtils.isEmpty(mysqlLogisticList)) {
                    break;
                }
                total += mysqlLogisticList.size();
                Assert.isTrue(total <= 500, "[商城] 轮循更新db物流信息数最大500");
                // 根据租户id分组处理物流数据
                Map<Integer, List<OmsLogistic>> groupByTenantId = mysqlLogisticList.stream().collect(Collectors.groupingBy(OmsLogistic::getTenantId));
                groupByTenantId.forEach((tenantId, list) -> {
                    TenantIdContext.setTenantId(tenantId);
                    // 组装查询参数
                    List<MallLogisticsDTO> queryParamList = Lists.newLinkedList();
                    list.forEach(item -> queryParamList.add(MallLogisticsDTO.builder()
                            .logisticsCode(item.getLogisticsCode())
                            .logisticsNo(item.getLogisticsNo())
                            .receiverPhone(item.getReceiverPhone())
                            .build()));
                    // 查询物流信息
                    Map<String, TpsLogisticsVO> logisticInfoMap = this.getLogisticListByRpc(queryParamList);
                    List<OmsLogistic> updateList = Lists.newLinkedList();
                    for (OmsLogistic item : list) {
                        String key = item.getLogisticsCode() + item.getLogisticsNo();
                        TpsLogisticsVO tpsLogisticsVO = logisticInfoMap.get(key);
                        if (tpsLogisticsVO != null) {
                            item.setTraceJson(JSON.toJSONString(tpsLogisticsVO.getTraceList()));
                            item.setDelivererName(tpsLogisticsVO.getDelivererName());
                            item.setDelivererPhone(tpsLogisticsVO.getDelivererPhone());
                            item.setLocation(tpsLogisticsVO.getLocation());
                            item.setStatus(tpsLogisticsVO.getStatus());
                            item.setStatusEx(tpsLogisticsVO.getStatusEx());
                        }
                        updateList.add(item);
                    }
                    // 更新数据库中的物流信息
                    if (!CollectionUtils.isEmpty(updateList)) {
                        super.updateBatchById(updateList);
                    }
                });
            } catch (Exception e) {
                log.error("[商城] 轮循更新db物流信息异常 page:{} pageSize:{} total:{} error:{}", page, pageSize, total, e.getMessage());
                return;
            }
        }
        log.info("[商城] 轮循更新db物流信息结束 page:{} pageSize:{} total:{}", page, pageSize, total);
    }

    /**
     * 查询物流信息 - 通过第三方服务接口
     *
     * @param params 提交参数
     * @return 快递公司编码+快递单号 -> 物流信息
     * @author zhengqingya
     * @date 2021/10/26 9:35
     */
    private Map<String, TpsLogisticsVO> getLogisticListByRpc(List<MallLogisticsDTO> params) {
        Map<String, TpsLogisticsVO> resultMap = Maps.newHashMap();
        // FIXME 查询第三方物流数据
        List<TpsLogisticsVO> list = Lists.newArrayList();
        list.forEach(item -> resultMap.put(item.getLogisticsCode() + item.getLogisticsNo(), item));
        return resultMap;
    }

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 详情
     * @author zhengqingya
     * @date 2021/10/26 15:01
     */
    private TpsLogisticsVO getLogisticByRpc(MallLogisticsDTO params) {
        String logisticsCode = params.getLogisticsCode();
        String logisticsNo = params.getLogisticsNo();
        Map<String, TpsLogisticsVO> logisticInfoMap = this.getLogisticListByRpc(Lists.newArrayList(params));
        TpsLogisticsVO tpsLogisticsVO = logisticInfoMap.get(logisticsCode + logisticsNo);
        Assert.notNull(tpsLogisticsVO, "该物流数据不存在！");
        return tpsLogisticsVO;
    }

}
