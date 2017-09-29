package com.nandi.disastermanager.search.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qingsong on 2017/9/21.
 */
@Entity
public class MonitorPoint {
    @Id
    private Long id;
    private String time;
    private String monitorId;
    private String name;
    private double monitorData;
    @Generated(hash = 2037224672)
    public MonitorPoint(Long id, String time, String monitorId, String name,
            double monitorData) {
        this.id = id;
        this.time = time;
        this.monitorId = monitorId;
        this.name = name;
        this.monitorData = monitorData;
    }
    @Generated(hash = 32220235)
    public MonitorPoint() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getMonitorId() {
        return this.monitorId;
    }
    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getMonitorData() {
        return this.monitorData;
    }
    public void setMonitorData(double monitorData) {
        this.monitorData = monitorData;
    }
}
