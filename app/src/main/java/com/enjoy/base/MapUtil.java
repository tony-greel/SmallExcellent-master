package com.enjoy.base;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;

/**
 * Created by hyc on 2017/4/20 17:28
 */

public class MapUtil {
    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

}
