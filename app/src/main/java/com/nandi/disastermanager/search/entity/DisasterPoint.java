package com.nandi.disastermanager.search.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by lemon on 2017/9/7.
 * 建数据库表
 */
@Entity
public class DisasterPoint {
    @Id
    private Long id;
    /**编号**/
    private String disasterCode;
    /**经度**/
    private String lon;
    /**纬度**/
    private String lat;
    /**州市**/
    private String city;
    /**区县编号**/
    private String areaCode;
    /**区县**/
    private String county;
    /**乡镇**/
    private String town;
    /**等级**/
    private String threatLevel;
    /**类型**/
    private String type;
    /**形成原因**/
    private String inducement;
    /**名称**/
    private String disasterName;
    /**导航纬度**/
    private String LATITUDE;
    /**导航经度**/
    private String LONGITUDE;

    @Generated(hash = 265713837)
    public DisasterPoint(Long id, String disasterCode, String lon, String lat,
            String city, String areaCode, String county, String town,
            String threatLevel, String type, String inducement, String disasterName,
            String LATITUDE, String LONGITUDE) {
        this.id = id;
        this.disasterCode = disasterCode;
        this.lon = lon;
        this.lat = lat;
        this.city = city;
        this.areaCode = areaCode;
        this.county = county;
        this.town = town;
        this.threatLevel = threatLevel;
        this.type = type;
        this.inducement = inducement;
        this.disasterName = disasterName;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
    }
    @Generated(hash = 410069605)
    public DisasterPoint() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLon() {
        return this.lon;
    }
    public void setLon(String lon) {
        this.lon = lon;
    }
    public String getAreaCode() {
        return this.areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public String getDisasterCode() {
        return this.disasterCode;
    }
    public void setDisasterCode(String disasterCode) {
        this.disasterCode = disasterCode;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getLat() {
        return this.lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getDisasterName() {
        return this.disasterName;
    }
    public void setDisasterName(String disasterName) {
        this.disasterName = disasterName;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getThreatLevel() {
        return this.threatLevel;
    }
    public void setThreatLevel(String threatLevel) {
        this.threatLevel = threatLevel;
    }
    public String getInducement() {
        return this.inducement;
    }
    public void setInducement(String inducement) {
        this.inducement = inducement;
    }
    public String getLATITUDE() {
        return this.LATITUDE;
    }
    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }
    public String getLONGITUDE() {
        return this.LONGITUDE;
    }
    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }
    public String getCounty() {
        return this.county;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public String getTown() {
        return this.town;
    }
    public void setTown(String town) {
        this.town = town;
    }

}
