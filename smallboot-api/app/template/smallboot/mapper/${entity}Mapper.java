package ${package.mapper};

import java.util.List;
import ${package.vo}.${entity}DetailVO;
import ${package.vo}.${entity}PageVO;
import ${package.dto}.${entity}PageDTO;
import ${package.dto}.${entity}DetailDTO;
import ${package.entity}.${entity};

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p> ${tableComment} Mapper </p>
 *
 * @author ${author}
 * @description
 * @date ${date}
 */
public interface ${entity}Mapper extends BaseMapper<${entity}> {

    /**
     * 列表分页
     *
     * @param page 分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author ${author}
     * @date ${date}
     */
    IPage<${entity}PageVO> selectDataPage(IPage page, @Param("filter") ${entity}PageDTO filter);

    /**
     * 详情
     *
     * @param filter 查询过滤参数
     * @return 详情
     * @author ${author}
     * @date ${date}
     */
     ${entity}DetailVO detail(@Param("filter") ${entity}DetailDTO filter);

}
