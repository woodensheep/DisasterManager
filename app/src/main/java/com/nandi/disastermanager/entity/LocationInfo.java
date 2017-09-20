package com.nandi.disastermanager.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by ChenPeng on 2017/9/19.
 */
@Entity
public class LocationInfo {
    @Id
    private Long id;
    private String userName;
    private String lonAndLat;
    private String startTime;
    private String endTime;
    @Generated(hash = 730276121)
    public LocationInfo(Long id, String userName, String lonAndLat,
                        String startTime, String endTime) {
        this.id = id;
        this.userName = userName;
        this.lonAndLat = lonAndLat;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    @Generated(hash = 1054559726)
    public LocationInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getLonAndLat() {
        return this.lonAndLat;
    }
    public void setLonAndLat(String lonAndLat) {
        this.lonAndLat = lonAndLat;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
