package com.zhengqing.app.api;

import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.redis.model.bo.RedisGeoPoint;
import com.zhengqing.common.redis.util.RedisGeoUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p> 测试redis geo </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/29 17:46
 */
@Slf4j
@RestController
@RequestMapping("/api/test/redis/geo")
@Api(tags = "test-redis-geo-api")
public class TestRedisGeoController extends BaseController {

    private final String GEO_KEY = "test_geo_key";

    private String buildRedisKey(String key, String cityId) {
        return key + ":" + cityId;
    }

    /**
     * 添加位置
     * 成都银泰城       http://localhost:20040/web/api/demo/redis/geo/addDriverPosition?cityId=666&driverId=001&lng=104.059477&lat=30.540611
     * 腾讯成都大夏B楼   http://localhost:20040/web/api/demo/redis/geo/addDriverPosition?cityId=666&driverId=002&lng=104.061967&lat=30.545361
     * 成都高新文化中心  http://localhost:20040/web/api/demo/redis/geo/addDriverPosition?cityId=666&driverId=003&lng=104.050658&lat=30.543457
     * 大源            http://localhost:20040/web/api/demo/redis/geo/addDriverPosition?cityId=666&driverId=004&lng=104.046002&lat=30.549537
     */
    @GetMapping("geoAdd")
    public List<Point> geoAdd(String cityId, String driverId, Double lng, Double lat) {
        String redisKey = this.buildRedisKey(this.GEO_KEY, cityId);
        RedisGeoUtil.geoAdd(redisKey, new Point(lng, lat), driverId);
        return RedisGeoUtil.geoPos(redisKey, driverId);
    }

    /**
     * 查询1km内的数据
     * OCG国际中心   http://localhost:20040/web/api/demo/redis/geo/getNearDrivers?cityId=666&lng=104.064391&lat=30.542866
     */
    @GetMapping("geoNear")
    public List<RedisGeoPoint> geoNear(String cityId, Double lng, Double lat) {
        String redisKey = this.buildRedisKey(this.GEO_KEY, cityId);
        return RedisGeoUtil.geoNear(redisKey, lng, lat, 1, RedisGeoCommands.DistanceUnit.KILOMETERS, 5);
    }

}

