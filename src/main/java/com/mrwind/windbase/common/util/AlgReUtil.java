package com.mrwind.windbase.common.util;

import java.text.DecimalFormat;

/**
 * 算法重构类
 * Created by admin on 16/11/10.
 */
public class AlgReUtil {

    private static final int INIT_SCOPE = 3000;//范围,单位米
    private static final int MISSON_SCOPE = 8;//任务范围,单位公里
    private static final int DIRECT_TIMES = 3;//任务范围,单位公里
    private static double EARTH_RADIUS = 6378.137;// 地球半径

    /**
     * 构造圆形数据
     * @param fromlng
     * @param fromLat
     * @param i
     * @param geomStr
     * @return
     */
    private String getGeoStr(Double fromlng, Double fromLat, int i, String geomStr) {
        Coord[] coords = createCircleByPntDistance(fromlng, fromLat, INIT_SCOPE *i);
        for(Coord coord : coords){
            if(geomStr.isEmpty()){
                geomStr = coord.getLng() + "," + coord.getLat();
            }else {
                geomStr += ";" + coord.getLng() + "," + coord.getLat();
            }
        }
        return geomStr;
    }

    /**
     * 根据坐标构造六边形数据
     * @param lng
     * @param lat
     * @param distance 单位：米
     * @return
     */
    public static Coord[] createCircleByPntDistance(double lng, double lat, double distance){
        double RADIUS = getDegreeByDistance(distance);

        final int SIDES = 32;// 圆上面的点个数
        Coord coords[] = new Coord[SIDES + 1];
        for (int i = 0; i < SIDES; i++) {
            double angle = ((double) i / (double) SIDES) * Math.PI * 2.0;
            double dx = Math.cos(angle) * RADIUS;
            double dy = Math.sin(angle) * RADIUS;
            coords[i] = new Coord((double) lng + dx, (double) lat + dy);
        }
        coords[SIDES] = coords[0];
        return coords;
    }

    /**
     * @param distance 单位：米
     * @return
     */
    public static double getDegreeByDistance(double distance) {
        double r = 6371.004 * 1000;
        double degree = distance * 180 / (Math.PI * r);
        return degree;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 返回两点之间距离，单位：m,保留三维小数
     */
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        Double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        DecimalFormat df = new DecimalFormat("0.000");
        s = Double.parseDouble(df.format((double)Math.round(s * 1000)));

        return s;
    }


}
