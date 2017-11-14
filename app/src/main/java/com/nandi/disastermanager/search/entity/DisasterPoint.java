package com.nandi.disastermanager.search.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by lemon on 2017/9/7.
 * 建数据库表
 */
@Entity
public class DisasterPoint implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String threatObject;
    private String threatMoney;
    private String disasterNum;
    private String disasterSite;
    private int userid;
    private int adminid;
    private String monitorPersonnel;
    private String threatNum;
    private String city;
    private String formationTime;
    private String phoneNum;
    private int level;
    private String county;
    private String majorIncentives;
    private String area_name;
    private double disasterLat;
    private String province;
    private String tableTime;
    private String disasterType;
    private double disasterLon;
    private String investigationUnit;
    private String town;
    private String disasterGrade;
    private String disasterAdress;
    private String disasterName;
    @Generated(hash = 1342509694)
    public DisasterPoint(Long id, String threatObject, String threatMoney,
            String disasterNum, String disasterSite, int userid, int adminid,
            String monitorPersonnel, String threatNum, String city,
            String formationTime, String phoneNum, int level, String county,
            String majorIncentives, String area_name, double disasterLat,
            String province, String tableTime, String disasterType,
            double disasterLon, String investigationUnit, String town,
            String disasterGrade, String disasterAdress, String disasterName) {
        this.id = id;
        this.threatObject = threatObject;
        this.threatMoney = threatMoney;
        this.disasterNum = disasterNum;
        this.disasterSite = disasterSite;
        this.userid = userid;
        this.adminid = adminid;
        this.monitorPersonnel = monitorPersonnel;
        this.threatNum = threatNum;
        this.city = city;
        this.formationTime = formationTime;
        this.phoneNum = phoneNum;
        this.level = level;
        this.county = county;
        this.majorIncentives = majorIncentives;
        this.area_name = area_name;
        this.disasterLat = disasterLat;
        this.province = province;
        this.tableTime = tableTime;
        this.disasterType = disasterType;
        this.disasterLon = disasterLon;
        this.investigationUnit = investigationUnit;
        this.town = town;
        this.disasterGrade = disasterGrade;
        this.disasterAdress = disasterAdress;
        this.disasterName = disasterName;
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
    public String getThreatObject() {
        return this.threatObject;
    }
    public void setThreatObject(String threatObject) {
        this.threatObject = threatObject;
    }
    public String getThreatMoney() {
        return this.threatMoney;
    }
    public void setThreatMoney(String threatMoney) {
        this.threatMoney = threatMoney;
    }
    public String getDisasterNum() {
        return this.disasterNum;
    }
    public void setDisasterNum(String disasterNum) {
        this.disasterNum = disasterNum;
    }
    public String getDisasterSite() {
        return this.disasterSite;
    }
    public void setDisasterSite(String disasterSite) {
        this.disasterSite = disasterSite;
    }
    public int getUserid() {
        return this.userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public int getAdminid() {
        return this.adminid;
    }
    public void setAdminid(int adminid) {
        this.adminid = adminid;
    }
    public String getMonitorPersonnel() {
        return this.monitorPersonnel;
    }
    public void setMonitorPersonnel(String monitorPersonnel) {
        this.monitorPersonnel = monitorPersonnel;
    }
    public String getThreatNum() {
        return this.threatNum;
    }
    public void setThreatNum(String threatNum) {
        this.threatNum = threatNum;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getFormationTime() {
        return this.formationTime;
    }
    public void setFormationTime(String formationTime) {
        this.formationTime = formationTime;
    }
    public String getPhoneNum() {
        return this.phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public int getLevel() {
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getCounty() {
        return this.county;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public String getMajorIncentives() {
        return this.majorIncentives;
    }
    public void setMajorIncentives(String majorIncentives) {
        this.majorIncentives = majorIncentives;
    }
    public String getArea_name() {
        return this.area_name;
    }
    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
    public double getDisasterLat() {
        return this.disasterLat;
    }
    public void setDisasterLat(double disasterLat) {
        this.disasterLat = disasterLat;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getTableTime() {
        return this.tableTime;
    }
    public void setTableTime(String tableTime) {
        this.tableTime = tableTime;
    }
    public String getDisasterType() {
        return this.disasterType;
    }
    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }
    public double getDisasterLon() {
        return this.disasterLon;
    }
    public void setDisasterLon(double disasterLon) {
        this.disasterLon = disasterLon;
    }
    public String getInvestigationUnit() {
        return this.investigationUnit;
    }
    public void setInvestigationUnit(String investigationUnit) {
        this.investigationUnit = investigationUnit;
    }
    public String getTown() {
        return this.town;
    }
    public void setTown(String town) {
        this.town = town;
    }
    public String getDisasterGrade() {
        return this.disasterGrade;
    }
    public void setDisasterGrade(String disasterGrade) {
        this.disasterGrade = disasterGrade;
    }
    public String getDisasterAdress() {
        return this.disasterAdress;
    }
    public void setDisasterAdress(String disasterAdress) {
        this.disasterAdress = disasterAdress;
    }
    public String getDisasterName() {
        return this.disasterName;
    }
    public void setDisasterName(String disasterName) {
        this.disasterName = disasterName;
    }

    @Override
    public String toString() {
        return "DisasterPoint{" +
                "id=" + id +
                ", threatObject='" + threatObject + '\'' +
                ", threatMoney='" + threatMoney + '\'' +
                ", disasterNum='" + disasterNum + '\'' +
                ", disasterSite='" + disasterSite + '\'' +
                ", userid=" + userid +
                ", adminid=" + adminid +
                ", monitorPersonnel='" + monitorPersonnel + '\'' +
                ", threatNum='" + threatNum + '\'' +
                ", city='" + city + '\'' +
                ", formationTime='" + formationTime + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", level=" + level +
                ", county='" + county + '\'' +
                ", majorIncentives='" + majorIncentives + '\'' +
                ", area_name='" + area_name + '\'' +
                ", disasterLat=" + disasterLat +
                ", province='" + province + '\'' +
                ", tableTime='" + tableTime + '\'' +
                ", disasterType='" + disasterType + '\'' +
                ", disasterLon=" + disasterLon +
                ", investigationUnit='" + investigationUnit + '\'' +
                ", town='" + town + '\'' +
                ", disasterGrade='" + disasterGrade + '\'' +
                ", disasterAdress='" + disasterAdress + '\'' +
                ", disasterName='" + disasterName + '\'' +
                '}';
    }
}
