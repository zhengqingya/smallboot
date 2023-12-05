package com.zhengqing.system.service;

import com.zhengqing.system.model.vo.SysCgProjectPackageVO;

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
    SysCgProjectPackageVO projectPackage();

}
