package com.zhengqing.system.service;

import com.zhengqing.system.model.vo.SysCgProjectPackageTreeVO;

import java.util.List;

/**
 * <p> 代码生成器 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
public interface ICodeGenerateService {

    /**
     * 项目包
     *
     * @return 树形包结构
     * @author zhengqingya
     * @date 2023/12/5 9:28
     */
    List<SysCgProjectPackageTreeVO> projectPackageTree();

    /**
     * 生成代码
     *
     * @return void
     * @author zhengqingya
     * @date 2023/12/5 9:28
     */
    void generateTplData();

}
