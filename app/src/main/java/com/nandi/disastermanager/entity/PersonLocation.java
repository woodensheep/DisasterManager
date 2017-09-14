package com.nandi.disastermanager.entity;

/**
 * Created by ChenPeng on 2017/7/21.
 */

public class PersonLocation {
    /**
     * 纬度
     */
    private String lat;
    /**
     * 经度
     */
    private String lon;
    /**
     * id
     */
    private int id;
    //四重人员 type 1:群测群防 2：驻守 3:负责人 4：地环站
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
