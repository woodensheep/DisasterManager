package com.nandi.disastermanager.entity;

/**
 * Created by ChenPeng on 2017/7/11.
 */

public class DisasterPoint {


    /**
     * id : 62684
     * dis_name : 关山沟滑坡
     * dis_lon : 106.8088
     * dis_lat : 28.8683
     * dis_state : 1
     * dis_type : 1
     */

    private int id;
    private String dis_name;
    private String dis_lon;
    private String dis_lat;
    private int dis_state;
    private int dis_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDis_name() {
        return dis_name;
    }

    public void setDis_name(String dis_name) {
        this.dis_name = dis_name;
    }

    public String getDis_lon() {
        return dis_lon;
    }

    public void setDis_lon(String dis_lon) {
        this.dis_lon = dis_lon;
    }

    public String getDis_lat() {
        return dis_lat;
    }

    public void setDis_lat(String dis_lat) {
        this.dis_lat = dis_lat;
    }

    public int getDis_state() {
        return dis_state;
    }

    public void setDis_state(int dis_state) {
        this.dis_state = dis_state;
    }

    public int getDis_type() {
        return dis_type;
    }

    public void setDis_type(int dis_type) {
        this.dis_type = dis_type;
    }
}
