package com.zhengqing.app.api;

import cn.hutool.json.JSONUtil;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.limit.ApiLimit;
import com.zhengqing.system.mapper.SysUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p> 测试接口 </p>
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
public class TestAppController extends BaseController {

    private final SysUserMapper sysUserMapper;

    /**
     * python sqlmap.py -u "http://127.0.0.1:888/api/test/sql?username=1" --batch --banner
     */
    @GetMapping("sql")
    @ApiOperation("sql注入")
    public Object testSqlmap(@RequestParam String username) {
        return this.sysUserMapper.testSqlmap(username);
    }

    @PostMapping("testMapSpringEl（获取map值）")
    @ApiLimit(key = "'test' + ':' + #params['id']")
    public void testMapSpringEl(@RequestBody Map<String, String> params) {
        log.info("testMapSpringEl: " + JSONUtil.toJsonStr(params));
    }

    @GetMapping("pathVariable/{id}/{name}")
    @ApiOperation("@PathVariable路径获取")
    public Object pathVariable(@PathVariable Integer id, @PathVariable String name, HttpServletRequest request) {
//        return request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern");
        return request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
    }

}
