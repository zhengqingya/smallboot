package com.zhengqing.app.api;

import com.zhengqing.common.auth.custom.open.ApiOpen;
import com.zhengqing.common.base.constant.ServiceConstant;
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
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_TEST + "/redis/geo")
@Api(tags = "test-redis-geo-api")
public class TestRedisGeoController extends BaseController {

    private final String GEO_KEY = "test_geo_key";

    private String buildRedisKey(String key, String cityId) {
        return key + ":" + cityId;
    }

    /**
     * 添加位置
     * 成都银泰城       http://localhost:888/api/test/redis/geo/geoAdd?cityId=666&driverId=001&lng=104.059477&lat=30.540611
     * 腾讯成都大夏B楼   http://localhost:888/api/test/redis/geo/geoAdd?cityId=666&driverId=002&lng=104.061967&lat=30.545361
     * 成都高新文化中心  http://localhost:888/api/test/redis/geo/geoAdd?cityId=666&driverId=003&lng=104.050658&lat=30.543457
     * 大源            http://localhost:888/api/test/redis/geo/geoAdd?cityId=666&driverId=004&lng=104.046002&lat=30.549537
     */
    @ApiOpen
    @GetMapping("geoAdd")
    public List<Point> geoAdd(String cityId, String driverId, Double lng, Double lat) {
        String redisKey = this.buildRedisKey(this.GEO_KEY, cityId);
        RedisGeoUtil.geoAdd(redisKey, new Point(lng, lat), driverId);
        return RedisGeoUtil.geoPos(redisKey, driverId);
    }

    /**
     * 查询1km内的数据
     * OCG国际中心   http://localhost:888/api/test/redis/geo/geoNear?cityId=666&lng=104.064391&lat=30.542866
     */
    @ApiOpen
    @GetMapping("geoNear")
    public List<RedisGeoPoint> geoNear(String cityId, Double lng, Double lat) {
        String redisKey = this.buildRedisKey(this.GEO_KEY, cityId);
        return RedisGeoUtil.geoNear(redisKey, lng, lat, 1, RedisGeoCommands.DistanceUnit.KILOMETERS, 5);
    }

    /**
     * 查询2点距离
     * http://localhost:888/api/test/redis/geo/geoDist?cityId=666&m1=001&m2=002
     */
    @ApiOpen
    @GetMapping("geoDist")
    public Object geoDist(String cityId, String m1, String m2) {
        String redisKey = this.buildRedisKey(this.GEO_KEY, cityId);
        return RedisGeoUtil.geoDist(redisKey, m1, m2, RedisGeoCommands.DistanceUnit.METERS);
    }

    /**
     * 查询2点距离
     * http://localhost:888/api/test/redis/geo/geoDistance?aLng=104.059477&aLat=30.540611&bLng=104.061967&bLat=30.545361
     */
    @ApiOpen
    @GetMapping("geoDistance")
    public Object geoDistance(Double aLng, Double aLat, Double bLng, Double bLat) {
        return RedisGeoUtil.geoDist(aLng, aLat, bLng, bLat, RedisGeoCommands.DistanceUnit.METERS);
    }

}

