package com.zhengqing.app;

import com.zhengqing.mall.model.bo.LngLatBO;
import com.zhengqing.mall.util.GeoUtil;
import org.junit.Test;

import java.text.DecimalFormat;

/**
 * <p> Geo距离计算 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/9/26 17:14
 */
public class GeoTest {
    @Test
    public void test() throws Exception {
        Double distanceMeters = GeoUtil.getDistanceMeters(
                // 天府三街
                new LngLatBO(104.07520, 30.55262),
                // 天府五街
                new LngLatBO(104.07547, 30.54295)
        );

        // 格式化-保留两位小数
        DecimalFormat df = new DecimalFormat("#.00");
        String distanceStr = df.format(distanceMeters / 1000);

        System.out.println("WGS84坐标系计算结果：" + distanceStr + "km");
    }
}
