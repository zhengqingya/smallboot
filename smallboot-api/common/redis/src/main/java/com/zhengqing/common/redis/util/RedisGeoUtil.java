package com.zhengqing.common.redis.util;

import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Lists;
import com.zhengqing.common.redis.model.bo.RedisGeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Redis GEO 工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/11/27 14:38
 */
@Service
public class RedisGeoUtil {

    private static StringRedisTemplate redisTemplate;

    @Autowired
    public RedisGeoUtil(StringRedisTemplate redisTemplate) {
        RedisGeoUtil.redisTemplate = redisTemplate;
    }

    /**
     * 添加经纬度信息
     * redis命令： geoadd key 116.405285 39.904989 "北京"
     *
     * @param key    key
     * @param point  坐标
     * @param member 成员
     * @return java.lang.Long
     * @author zhengqingya
     * @date 2019/11/27 15:53
     */
    public static Long geoAdd(String key, Point point, String member) {
//        if (redisTemplate.hasKey(key)) {
//            geoDel(key, member);
//        }
        return redisTemplate.opsForGeo().add(key, point, member);
    }

    /**
     * 删除经纬度信息
     *
     * @param key    key
     * @param member 成员
     * @return java.lang.Long
     * @author zhengqingya
     * @date 2019/11/27 15:53
     */
    public static Long geoDel(String key, String member) {
        return redisTemplate.opsForGeo().remove(key, member);
    }

    /**
     * 查找指定key的经纬度信息，可以指定多个member，批量返回
     * redis命令： geopos key 北京
     *
     * @param key     key
     * @param members 成员
     * @return 坐标
     * @author zhengqingya
     * @date 2019/11/27 15:57
     */
    public static List<Point> geoPos(String key, String... members) {
        return redisTemplate.opsForGeo().position(key, members);
    }

    /**
     * 返回两个位置的距离，可以指定单位，比如米m，千米km，英里mi，英尺ft
     * redis命令： geodist key 北京 上海 km
     *
     * @param key     key
     * @param member1 成员1
     * @param member2 成员2
     * @param metric  距离单位 {@link RedisGeoCommands.DistanceUnit}
     * @return 距离
     * @author zhengqingya
     * @date 2019/11/27 15:58
     */
    public static Distance geoDist(String key, String member1, String member2, Metric metric) {
        return redisTemplate.opsForGeo().distance(key, member1, member2, metric);
    }

    /**
     * 返回两个位置的距离
     *
     * @param aLng   成员1经度
     * @param aLat   成员1纬度
     * @param bLng   成员2经度
     * @param bLat   成员2纬度
     * @param metric 距离单位 {@link RedisGeoCommands.DistanceUnit}
     * @return 距离
     * @author zhengqingya
     * @date 2019/11/27 15:58
     */
    public static Distance geoDist(Double aLng, Double aLat, Double bLng, Double bLat, Metric metric) {
        String key = "redis_geo_two_point_distance:" + IdUtil.getSnowflakeNextId();
        geoAdd(key, new Point(aLng, aLat), "member1");
        geoAdd(key, new Point(bLng, bLat), "member2");
        Distance distance = geoDist(key, "member1", "member2", metric);
        RedisUtil.delete(key);
        return distance;
    }

    /**
     * 根据给定的经纬度，返回半径不超过指定距离的元素
     * redis命令： georadius key 116.405285 39.904989 100 km WITHDIST WITHCOORD ASC
     *
     * @param key    key
     * @param circle 半径信息
     * @param count  限定返回的记录数
     * @return 满足条件的数据
     * @author zhengqingya
     * @date 2019/11/27 15:58
     */
    public static GeoResults<RedisGeoCommands.GeoLocation<String>> geoRadius(String key, Circle circle, long count) {
        // includeDistance 包含距离
        // includeCoordinates 包含经纬度
        // sortAscending 正序排序
        // limit 限定返回的记录数
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().includeCoordinates().sortAscending().limit(count);
        return redisTemplate.opsForGeo().radius(key, circle, args);
    }


    /**
     * 根据指定经纬度返回指定范围内最近的元素
     *
     * @param key           key
     * @param lng           经度
     * @param lat           纬度
     * @param distanceValue 距离值
     * @param metric        距离单位 {@link RedisGeoCommands.DistanceUnit}
     * @param count         限定返回的记录数
     * @return 满足条件的数据
     * @author zhengqingya
     * @date 2019/11/27 15:58
     */
    public static List<RedisGeoPoint> geoNear(String key, Double lng, Double lat,
                                              double distanceValue, Metric metric, long count) {
        Circle circle = new Circle(new Point(lng, lat), new Distance(distanceValue, metric));
        GeoResults<RedisGeoCommands.GeoLocation<String>> geoLocationList = RedisGeoUtil.geoRadius(key, circle, count);
        List<RedisGeoPoint> resultList = Lists.newLinkedList();
        geoLocationList.forEach(item -> {
            RedisGeoCommands.GeoLocation<String> location = item.getContent();
            Point point = location.getPoint();
            RedisGeoPoint position = RedisGeoPoint.builder()
                    .key(key)
                    .member(location.getName())
                    .lng(point.getX())
                    .lat(point.getY())
                    .build();
            resultList.add(position);
        });
        return resultList;
    }

    /**
     * 根据指定的地点查询半径在指定范围内的位置
     * redis命令： georadiusbymember key 北京 100 km WITHDIST WITHCOORD ASC COUNT 5
     *
     * @param key      key
     * @param member   成员
     * @param distance 距离
     * @param count    限定返回的记录数
     * @return 满足条件的数据
     * @author zhengqingya
     * @date 2019/11/27 15:58
     */
    public static GeoResults<RedisGeoCommands.GeoLocation<String>> geoRadiusByMember(
            String key, String member, Distance distance, long count) {
        // includeDistance 包含距离
        // includeCoordinates 包含经纬度
        // sortAscending 正序排序
        // limit 限定返回的记录数
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().includeCoordinates().sortAscending().limit(count);
        return redisTemplate.opsForGeo().radius(key, member, distance, args);
    }

    /**
     * 获取一个或多个位置元素的 geohash 值
     * redis命令： geohash key 北京
     *
     * @param key     key
     * @param members 成员
     * @return 结果
     * @author zhengqingya
     * @date 2019/11/27 16:13
     */
    public static List<String> geoHash(String key, String... members) {
        return redisTemplate.opsForGeo().hash(key, members);
    }

}
