package com.nandi.disastermanager.search.entity;

import java.util.List;

/**
 * Created by lemon on 2017/9/9.
 * 网络请求的数据
 */

public class DisasterData {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"threatObject":"村民及房屋","threatMoney":"190","disasterNum":"520402070031","disasterSite":"贬王村翁庆组","userid":1426,"adminid":301,"monitorPersonnel":"王朝学","threatNum":"75","city":"安顺市","formationTime":"","phoneNum":"13508532917","level":4,"county":"安顺市西秀区","majorIncentives":"自然","area_name":"杨武乡","disasterLat":25.983889,"province":"贵州省","tableTime":"","disasterType":"07","disasterLon":106.159722,"investigationUnit":"112地质队","town":"杨武乡","disasterGrade":"小型","disasterAdress":"安顺市西秀区杨武乡贬王村翁庆组","disasterName":"翁庆崩塌"}]
     */

    private MetaBean meta;
    private List<DataBean> data;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class MetaBean {
        /**
         * success : true
         * message : ok
         */

        private boolean success;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class DataBean {
        /**
         * threatObject : 村民及房屋
         * threatMoney : 190
         * disasterNum : 520402070031
         * disasterSite : 贬王村翁庆组
         * userid : 1426
         * adminid : 301
         * monitorPersonnel : 王朝学
         * threatNum : 75
         * city : 安顺市
         * formationTime :
         * phoneNum : 13508532917
         * level : 4
         * county : 安顺市西秀区
         * majorIncentives : 自然
         * area_name : 杨武乡
         * disasterLat : 25.983889
         * province : 贵州省
         * tableTime :
         * disasterType : 07
         * disasterLon : 106.159722
         * investigationUnit : 112地质队
         * town : 杨武乡
         * disasterGrade : 小型
         * disasterAdress : 安顺市西秀区杨武乡贬王村翁庆组
         * disasterName : 翁庆崩塌
         */

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

        public String getThreatObject() {
            return threatObject;
        }

        public void setThreatObject(String threatObject) {
            this.threatObject = threatObject;
        }

        public String getThreatMoney() {
            return threatMoney;
        }

        public void setThreatMoney(String threatMoney) {
            this.threatMoney = threatMoney;
        }

        public String getDisasterNum() {
            return disasterNum;
        }

        public void setDisasterNum(String disasterNum) {
            this.disasterNum = disasterNum;
        }

        public String getDisasterSite() {
            return disasterSite;
        }

        public void setDisasterSite(String disasterSite) {
            this.disasterSite = disasterSite;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getAdminid() {
            return adminid;
        }

        public void setAdminid(int adminid) {
            this.adminid = adminid;
        }

        public String getMonitorPersonnel() {
            return monitorPersonnel;
        }

        public void setMonitorPersonnel(String monitorPersonnel) {
            this.monitorPersonnel = monitorPersonnel;
        }

        public String getThreatNum() {
            return threatNum;
        }

        public void setThreatNum(String threatNum) {
            this.threatNum = threatNum;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getFormationTime() {
            return formationTime;
        }

        public void setFormationTime(String formationTime) {
            this.formationTime = formationTime;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getMajorIncentives() {
            return majorIncentives;
        }

        public void setMajorIncentives(String majorIncentives) {
            this.majorIncentives = majorIncentives;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public double getDisasterLat() {
            return disasterLat;
        }

        public void setDisasterLat(double disasterLat) {
            this.disasterLat = disasterLat;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getTableTime() {
            return tableTime;
        }

        public void setTableTime(String tableTime) {
            this.tableTime = tableTime;
        }

        public String getDisasterType() {
            return disasterType;
        }

        public void setDisasterType(String disasterType) {
            this.disasterType = disasterType;
        }

        public double getDisasterLon() {
            return disasterLon;
        }

        public void setDisasterLon(double disasterLon) {
            this.disasterLon = disasterLon;
        }

        public String getInvestigationUnit() {
            return investigationUnit;
        }

        public void setInvestigationUnit(String investigationUnit) {
            this.investigationUnit = investigationUnit;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getDisasterGrade() {
            return disasterGrade;
        }

        public void setDisasterGrade(String disasterGrade) {
            this.disasterGrade = disasterGrade;
        }

        public String getDisasterAdress() {
            return disasterAdress;
        }

        public void setDisasterAdress(String disasterAdress) {
            this.disasterAdress = disasterAdress;
        }

        public String getDisasterName() {
            return disasterName;
        }

        public void setDisasterName(String disasterName) {
            this.disasterName = disasterName;
        }
    }
}
