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
    private String disNum;
    private String phone;
    private String gather;
    @Generated(hash = 1606696482)
    public MonitorPoint(Long id, String time, String monitorId, String name,
            double monitorData, String disNum, String phone, String gather) {
        this.id = id;
        this.time = time;
        this.monitorId = monitorId;
        this.name = name;
        this.monitorData = monitorData;
        this.disNum = disNum;
        this.phone = phone;
        this.gather = gather;
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
    public String getDisNum() {
        return this.disNum;
    }
    public void setDisNum(String disNum) {
        this.disNum = disNum;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getGather() {
        return this.gather;
    }
    public void setGather(String gather) {
        this.gather = gather;
    }

}
