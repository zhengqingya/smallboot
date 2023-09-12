package ${package.service};

import java.util.List;
import ${package.vo}.${entity}DetailVO;
import ${package.vo}.${entity}PageVO;
import ${package.dto}.${entity}PageDTO;
import ${package.dto}.${entity}DetailDTO;
import ${package.dto}.${entity}SaveDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>  ${tableComment} 服务类 </p>
 *
 * @author ${author}
 * @description
 * @date ${date}
 */
public interface I${entity}Service extends IService<${entity}> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author ${author}
     * @date ${date}
     */
    IPage<${entity}PageVO> page(${entity}PageDTO params);

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 详情
     * @author ${author}
     * @date ${date}
     */
     ${entity}DetailVO detail(${entity}DetailDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author ${author}
     * @date ${date}
     */
     void addOrUpdateData(${entity}SaveDTO params);

     /**
      * 删除数据
      *
      * @param ${primaryColumnNameJavaLower} 主键ID
      * @return void
      * @author ${author}
      * @date ${date}
      */
      void deleteData(${primaryColumnTypeJava} ${primaryColumnNameJavaLower});

}
