package com.nandi.disastermanager.search.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qingsong on 2017/9/20.
 */
@Entity
public class MonitorListPoint {
    @Id
    private Long id;
    private String name;
    private String monitorId;
    private double lon;
    private String disNum;
    private double lat;
    private int reginId;
    @Generated(hash = 1896526880)
    public MonitorListPoint(Long id, String name, String monitorId, double lon,
            String disNum, double lat, int reginId) {
        this.id = id;
        this.name = name;
        this.monitorId = monitorId;
        this.lon = lon;
        this.disNum = disNum;
        this.lat = lat;
        this.reginId = reginId;
    }
    @Generated(hash = 1399322943)
    public MonitorListPoint() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMonitorId() {
        return this.monitorId;
    }
    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }
    public double getLon() {
        return this.lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public String getDisNum() {
        return this.disNum;
    }
    public void setDisNum(String disNum) {
        this.disNum = disNum;
    }
    public double getLat() {
        return this.lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public int getReginId() {
        return this.reginId;
    }
    public void setReginId(int reginId) {
        this.reginId = reginId;
    }

}
