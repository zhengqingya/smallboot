package com.zhengqing.mall.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.mall.entity.PmsSpuRate;
import com.zhengqing.mall.mapper.PmsSpuRateMapper;
import com.zhengqing.mall.mini.model.dto.MiniPmsSpuRatePageDTO;
import com.zhengqing.mall.mini.model.vo.MiniPmsSpuRatePageVO;
import com.zhengqing.mall.service.PmsSpuRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 商城-商品评价 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 17:10
 */
@Slf4j
@Service
public class PmsSpuRateServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<PmsSpuRateMapper, PmsSpuRate> implements PmsSpuRateService<PmsSpuRate> {

    @Resource
    private PmsSpuRateMapper pmsSpuRateMapper;

    @Override
    public IPage<MiniPmsSpuRatePageVO> page(MiniPmsSpuRatePageDTO params) {
        IPage<MiniPmsSpuRatePageVO> result = this.pmsSpuRateMapper.selectDataList(
                new Page<>(), params
        );
        List<MiniPmsSpuRatePageVO> list = result.getRecords();
        list.forEach(MiniPmsSpuRatePageVO::handleData);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(String spuRateId) {
        this.pmsSpuRateMapper.deleteById(spuRateId);
    }

}
