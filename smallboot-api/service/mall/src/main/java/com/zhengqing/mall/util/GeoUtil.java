package com.zhengqing.mall.util;

import com.zhengqing.mall.model.bo.LngLatBO;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * <p> Geo距离计算工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/13 10:59
 */
public class GeoUtil {


    /**
     * 计算2点距离
     *
     * @param start – starting coordinates
     * @param end   – ending coordinates
     * @return 距离 单位：m
     * @author zhengqingya
     * @date 2021/10/13 11:01
     */
    public static Double getDistanceMeters(LngLatBO start, LngLatBO end) {
        // 1、GCJ02(火星坐标系)转GPS84
        GCJ02toWGS84(start);
        GCJ02toWGS84(end);

        // 2、计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(
                Ellipsoid.WGS84,
                new GlobalCoordinates(start.getLatitude(), start.getLongitude()),
                new GlobalCoordinates(end.getLatitude(), end.getLongitude())
        );
        return geoCurve.getEllipsoidalDistance();
    }

    private static void GCJ02toWGS84(LngLatBO lngLat) {
        double[] doubles = Coordtransform.GCJ02toWGS84(lngLat.getLongitude(), lngLat.getLatitude());
        lngLat.setLongitude(doubles[0]);
        lngLat.setLatitude(doubles[1]);
    }

}
