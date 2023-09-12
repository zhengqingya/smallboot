package ${package.impl};

import cn.hutool.core.lang.Assert;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.utils.MyBeanUtils;
import ${package.vo}.${entity}DetailVO;
import ${package.vo}.${entity}PageVO;
import ${package.dto}.${entity}PageDTO;
import ${package.dto}.${entity}DetailDTO;
import ${package.dto}.${entity}SaveDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> ${tableComment} 服务实现类 </p>
 *
 * @author ${author}
 * @description
 * @date ${date}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ${entity}ServiceImpl extends ServiceImpl<${entity}Mapper, ${entity}> implements I${entity}Service {

    private final ${entity}Mapper ${entityNameLower}Mapper;

    @Override
    public IPage<${entity}PageVO> page(${entity}PageDTO params) {
        IPage<${entity}PageVO> result = this.${entityNameLower}Mapper.selectDataPage(new Page<>(), params);
        List<${entity}PageVO> list = result.getRecords();
        list.forEach(${entity}PageVO::handleData);
        return result;
    }

    @Override
    public ${entity}DetailVO detail(${entity}DetailDTO params){
        ${entity}DetailVO detailData = this.${entityNameLower}Mapper.detail(params);
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(${entity}SaveDTO params) {
<#list columnInfoList as item>
<#if item.columnNameDb != "create_by" && item.columnNameDb != "create_time" && item.columnNameDb != "update_by" && item.columnNameDb != "update_time" && item.columnNameDb != "is_deleted">
        ${item.columnTypeJava} ${item.columnNameJavaLower} = params.get${item.columnNameJavaUpper}();
</#if>
</#list>

        ${entity}.builder()
<#list columnInfoList as item>
<#if item.columnNameDb != "create_by" && item.columnNameDb != "create_time" && item.columnNameDb != "update_by" && item.columnNameDb != "update_time" && item.columnNameDb != "is_deleted">
            .${item.columnNameJavaLower}(${item.columnNameJavaLower})
</#if>
</#list>
            .build()
            .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(${primaryColumnTypeJava} ${primaryColumnNameJavaLower}){
        this.${entityNameLower}Mapper.deleteById(${primaryColumnNameJavaLower});
    }

}
