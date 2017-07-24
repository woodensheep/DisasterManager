package com.nandi.disastermanager;

/**
 * Created by ChenPeng on 2017/7/21.
 */

public class PersonLocation {
    private String lat;
    private String lon;
    private int id;

    public PersonLocation() {
    }

    public PersonLocation(String lat, String lon, int id) {
        this.lat = lat;
        this.lon = lon;
        this.id = id;
    }
    public String getLat() {
        return lat;
    }


    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
