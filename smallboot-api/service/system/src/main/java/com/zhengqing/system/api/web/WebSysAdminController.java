package com.zhengqing.system.api.web;

import com.zhengqing.common.auth.custom.open.ApiOpen;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.db.mapper.MyBaseMapper;
import com.zhengqing.system.model.vo.SysRoleReBtnPermListVO;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysPermBusinessService;
import com.zhengqing.system.service.ISysRoleMenuService;
import com.zhengqing.ums.feign.IUmsUserFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 管理员api
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/19 13:49
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/admin")
@Api(tags = "web-管理员api")
@RequiredArgsConstructor
public class WebSysAdminController extends BaseController {

    private final ISysPermBusinessService iSysPermBusinessService;
    private final MyBaseMapper myBaseMapper;
    private final ISysDictService iSysDictService;
    private final ISysRoleMenuService iSysRoleMenuService;
    private final IUmsUserFeignApi iUmsUserFeignApi;

    @ApiOperation("获取角色权限映射数据")
    @GetMapping("listRoleReBtnPerm")
    public ApiResult<List<SysRoleReBtnPermListVO>> listRoleReBtnPerm() {
        return ApiResult.ok(this.iSysRoleMenuService.listRoleReBtnPerm());
    }

    @ApiOperation("刷新Redis缓存中的角色菜单权限")
    @GetMapping("refreshRedisPerm")
    public ApiResult refreshRedisPerm() {
        this.iSysPermBusinessService.refreshRedisPerm();
        return ApiResult.ok();
    }

    /**
     * http://127.0.0.1:888/web/api/system/admin/initDb
     */
    @ApiOperation("初始化DB(谨慎操作)")
    @GetMapping("initDb")
    public ApiResult<Object> initDb() {
//        String initDbSql = "DROP DATABASE IF EXISTS `smallboot`; " +
//                "CREATE DATABASE `smallboot` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci; ";
//        String initSql = "USE `smallboot`; " + MyFileUtil.readFileContent(ProjectConstant.PROJECT_ROOT_DIRECTORY + "/doc/sql/smallboot.sql");
//        this.myBaseMapper.execSql(initDbSql);
//        this.myBaseMapper.execSql(initSql);
        return ApiResult.ok("OK");
    }

    /**
     * http://127.0.0.1:888/web/api/system/admin/testGetUmsApi
     */
    @ApiOpen
    @ApiOperation("测试调用ums接口")
    @GetMapping("testGetUmsApi")
    public ApiResult<Object> testGetUmsApi() {
        TenantIdContext.removeFlag();
        return ApiResult.ok(this.iUmsUserFeignApi.getUser(1L));
    }

}
