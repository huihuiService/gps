package com.twinmask.gps;

import java.sql.Timestamp;

public class GpsInfo {

    private String imei;

    private double lat;

    private double lng;

    private Timestamp gpsTime;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Timestamp getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(Timestamp gpsTime) {
        this.gpsTime = gpsTime;
    }
}
