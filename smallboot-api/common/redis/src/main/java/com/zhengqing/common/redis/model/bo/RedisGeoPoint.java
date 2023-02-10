package com.zhengqing.common.redis.model.bo;

import lombok.*;


/**
 * <p>
 * Redis GEO 坐标信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/11/27 14:38
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisGeoPoint {

    /**
     * key
     */
    private String key;
    /**
     * members
     */
    private String member;
    /**
     * 经度
     */
    private Double lng;
    /**
     * 纬度
     */
    private Double lat;

}
