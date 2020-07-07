package com.mrwind.windbase.common.util;

/**
 * Created by admin on 16/10/15.
 */
public class Coord {

    private double lng;//经度
    private double lat;//维度

    public Coord(){

    }

    public Coord(double lng, double lat){
        this.lng = lng;
        this.lat = lat;
    }


    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}

