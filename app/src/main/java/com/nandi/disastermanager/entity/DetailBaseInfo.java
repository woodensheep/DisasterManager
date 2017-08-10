package com.nandi.disastermanager.entity;

/**
 * Created by lemon on 2017/8/10.
 * 详细信息
 */

public class DetailBaseInfo {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"tabArea":{"id":11100,"areaParent":null,"unitId":null,"areaCode":null,"lon":null,"lat":null,"areaName":null},"id":11100,"disNo":"5001101020010101","disName":"关山沟滑坡","disType":1,"disState":1,"disLocation":"毛里村关山沟社","disLon":106.8088,"disLat":28.8683,"disX":"3195316","disY":"36383789","disCause":"77","disTime":"2004/06/01 00:00:00","disStratum":"J2s","disSurfaceType":"28","disSlope":"25","disArea":"0.42","disVolume":"1.26","disBefore":"570","disAfter":"595","imperilFamilies":2,"imperilMan":10,"imperilHouse":7,"imperilArea":"200","mainObject":"居民、村路","imperilMoney":"8","stableLevel":14,"imperilLevel":19,"dealIdea":"清除、搬迁","defenseLevel":42,"areaId":1188,"qcqfryId":14001,"warnMobile":"15702327885","hasMobile":1,"disRadius":1,"village":"毛里村关山沟社","bulu":0,"groupNo":"毛里村关山沟社","scale":17,"stateTime":"null","comeTime":"2015/12/21","operation":1,"statusNo":0,"videoName":"","remark":""}
     */

    private MetaBean meta;
    private DataBean data;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
         * tabArea : {"id":11100,"areaParent":null,"unitId":null,"areaCode":null,"lon":null,"lat":null,"areaName":null}
         * id : 11100
         * disNo : 5001101020010101
         * disName : 关山沟滑坡
         * disType : 1
         * disState : 1
         * disLocation : 毛里村关山沟社
         * disLon : 106.8088
         * disLat : 28.8683
         * disX : 3195316
         * disY : 36383789
         * disCause : 77
         * disTime : 2004/06/01 00:00:00
         * disStratum : J2s
         * disSurfaceType : 28
         * disSlope : 25
         * disArea : 0.42
         * disVolume : 1.26
         * disBefore : 570
         * disAfter : 595
         * imperilFamilies : 2
         * imperilMan : 10
         * imperilHouse : 7
         * imperilArea : 200
         * mainObject : 居民、村路
         * imperilMoney : 8
         * stableLevel : 14
         * imperilLevel : 19
         * dealIdea : 清除、搬迁
         * defenseLevel : 42
         * areaId : 1188
         * qcqfryId : 14001
         * warnMobile : 15702327885
         * hasMobile : 1
         * disRadius : 1
         * village : 毛里村关山沟社
         * bulu : 0
         * groupNo : 毛里村关山沟社
         * scale : 17
         * stateTime : null
         * comeTime : 2015/12/21
         * operation : 1
         * statusNo : 0
         * videoName :
         * remark :
         */

        private TabAreaBean tabArea;
        private int id;
        private String disNo;
        private String disName;
        private int disType;
        private int disState;
        private String disLocation;
        private double disLon;
        private double disLat;
        private String disX;
        private String disY;
        private String disCause;
        private String disTime;
        private String disStratum;
        private String disSurfaceType;
        private String disSlope;
        private String disArea;
        private String disVolume;
        private String disBefore;
        private String disAfter;
        private int imperilFamilies;
        private int imperilMan;
        private int imperilHouse;
        private String imperilArea;
        private String mainObject;
        private String imperilMoney;
        private int stableLevel;
        private int imperilLevel;
        private String dealIdea;
        private int defenseLevel;
        private int areaId;
        private int qcqfryId;
        private String warnMobile;
        private int hasMobile;
        private int disRadius;
        private String village;
        private int bulu;
        private String groupNo;
        private int scale;
        private String stateTime;
        private String comeTime;
        private int operation;
        private int statusNo;
        private String videoName;
        private String remark;

        public TabAreaBean getTabArea() {
            return tabArea;
        }

        public void setTabArea(TabAreaBean tabArea) {
            this.tabArea = tabArea;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDisNo() {
            return disNo;
        }

        public void setDisNo(String disNo) {
            this.disNo = disNo;
        }

        public String getDisName() {
            return disName;
        }

        public void setDisName(String disName) {
            this.disName = disName;
        }

        public int getDisType() {
            return disType;
        }

        public void setDisType(int disType) {
            this.disType = disType;
        }

        public int getDisState() {
            return disState;
        }

        public void setDisState(int disState) {
            this.disState = disState;
        }

        public String getDisLocation() {
            return disLocation;
        }

        public void setDisLocation(String disLocation) {
            this.disLocation = disLocation;
        }

        public double getDisLon() {
            return disLon;
        }

        public void setDisLon(double disLon) {
            this.disLon = disLon;
        }

        public double getDisLat() {
            return disLat;
        }

        public void setDisLat(double disLat) {
            this.disLat = disLat;
        }

        public String getDisX() {
            return disX;
        }

        public void setDisX(String disX) {
            this.disX = disX;
        }

        public String getDisY() {
            return disY;
        }

        public void setDisY(String disY) {
            this.disY = disY;
        }

        public String getDisCause() {
            return disCause;
        }

        public void setDisCause(String disCause) {
            this.disCause = disCause;
        }

        public String getDisTime() {
            return disTime;
        }

        public void setDisTime(String disTime) {
            this.disTime = disTime;
        }

        public String getDisStratum() {
            return disStratum;
        }

        public void setDisStratum(String disStratum) {
            this.disStratum = disStratum;
        }

        public String getDisSurfaceType() {
            return disSurfaceType;
        }

        public void setDisSurfaceType(String disSurfaceType) {
            this.disSurfaceType = disSurfaceType;
        }

        public String getDisSlope() {
            return disSlope;
        }

        public void setDisSlope(String disSlope) {
            this.disSlope = disSlope;
        }

        public String getDisArea() {
            return disArea;
        }

        public void setDisArea(String disArea) {
            this.disArea = disArea;
        }

        public String getDisVolume() {
            return disVolume;
        }

        public void setDisVolume(String disVolume) {
            this.disVolume = disVolume;
        }

        public String getDisBefore() {
            return disBefore;
        }

        public void setDisBefore(String disBefore) {
            this.disBefore = disBefore;
        }

        public String getDisAfter() {
            return disAfter;
        }

        public void setDisAfter(String disAfter) {
            this.disAfter = disAfter;
        }

        public int getImperilFamilies() {
            return imperilFamilies;
        }

        public void setImperilFamilies(int imperilFamilies) {
            this.imperilFamilies = imperilFamilies;
        }

        public int getImperilMan() {
            return imperilMan;
        }

        public void setImperilMan(int imperilMan) {
            this.imperilMan = imperilMan;
        }

        public int getImperilHouse() {
            return imperilHouse;
        }

        public void setImperilHouse(int imperilHouse) {
            this.imperilHouse = imperilHouse;
        }

        public String getImperilArea() {
            return imperilArea;
        }

        public void setImperilArea(String imperilArea) {
            this.imperilArea = imperilArea;
        }

        public String getMainObject() {
            return mainObject;
        }

        public void setMainObject(String mainObject) {
            this.mainObject = mainObject;
        }

        public String getImperilMoney() {
            return imperilMoney;
        }

        public void setImperilMoney(String imperilMoney) {
            this.imperilMoney = imperilMoney;
        }

        public int getStableLevel() {
            return stableLevel;
        }

        public void setStableLevel(int stableLevel) {
            this.stableLevel = stableLevel;
        }

        public int getImperilLevel() {
            return imperilLevel;
        }

        public void setImperilLevel(int imperilLevel) {
            this.imperilLevel = imperilLevel;
        }

        public String getDealIdea() {
            return dealIdea;
        }

        public void setDealIdea(String dealIdea) {
            this.dealIdea = dealIdea;
        }

        public int getDefenseLevel() {
            return defenseLevel;
        }

        public void setDefenseLevel(int defenseLevel) {
            this.defenseLevel = defenseLevel;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public int getQcqfryId() {
            return qcqfryId;
        }

        public void setQcqfryId(int qcqfryId) {
            this.qcqfryId = qcqfryId;
        }

        public String getWarnMobile() {
            return warnMobile;
        }

        public void setWarnMobile(String warnMobile) {
            this.warnMobile = warnMobile;
        }

        public int getHasMobile() {
            return hasMobile;
        }

        public void setHasMobile(int hasMobile) {
            this.hasMobile = hasMobile;
        }

        public int getDisRadius() {
            return disRadius;
        }

        public void setDisRadius(int disRadius) {
            this.disRadius = disRadius;
        }

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public int getBulu() {
            return bulu;
        }

        public void setBulu(int bulu) {
            this.bulu = bulu;
        }

        public String getGroupNo() {
            return groupNo;
        }

        public void setGroupNo(String groupNo) {
            this.groupNo = groupNo;
        }

        public int getScale() {
            return scale;
        }

        public void setScale(int scale) {
            this.scale = scale;
        }

        public String getStateTime() {
            return stateTime;
        }

        public void setStateTime(String stateTime) {
            this.stateTime = stateTime;
        }

        public String getComeTime() {
            return comeTime;
        }

        public void setComeTime(String comeTime) {
            this.comeTime = comeTime;
        }

        public int getOperation() {
            return operation;
        }

        public void setOperation(int operation) {
            this.operation = operation;
        }

        public int getStatusNo() {
            return statusNo;
        }

        public void setStatusNo(int statusNo) {
            this.statusNo = statusNo;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public static class TabAreaBean {
            /**
             * id : 11100
             * areaParent : null
             * unitId : null
             * areaCode : null
             * lon : null
             * lat : null
             * areaName : null
             */

            private int id;
            private Object areaParent;
            private Object unitId;
            private Object areaCode;
            private Object lon;
            private Object lat;
            private Object areaName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getAreaParent() {
                return areaParent;
            }

            public void setAreaParent(Object areaParent) {
                this.areaParent = areaParent;
            }

            public Object getUnitId() {
                return unitId;
            }

            public void setUnitId(Object unitId) {
                this.unitId = unitId;
            }

            public Object getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(Object areaCode) {
                this.areaCode = areaCode;
            }

            public Object getLon() {
                return lon;
            }

            public void setLon(Object lon) {
                this.lon = lon;
            }

            public Object getLat() {
                return lat;
            }

            public void setLat(Object lat) {
                this.lat = lat;
            }

            public Object getAreaName() {
                return areaName;
            }

            public void setAreaName(Object areaName) {
                this.areaName = areaName;
            }
        }
    }
}
