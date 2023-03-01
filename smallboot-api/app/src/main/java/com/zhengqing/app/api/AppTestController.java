package com.zhengqing.app.api;

import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.system.mapper.SysUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统管理 - 用户管理接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@Api(tags = "test-api")
public class AppTestController extends BaseController {

    private final SysUserMapper sysUserMapper;

    /**
     * python sqlmap.py -u "http://127.0.0.1:888/api/test/sql?username=1" --batch --banner
     */
    @GetMapping("sql")
    @ApiOperation("sql注入")
    public Object testSqlmap(@RequestParam String username) {
        return this.sysUserMapper.testSqlmap(username);
    }


}
