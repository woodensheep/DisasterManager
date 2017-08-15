package com.nandi.disastermanager.entity;

import java.util.List;

/**
 * Created by ChenPeng on 2017/7/21.
 */

public class PersonInfo {


    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"id":14091,"name":"杨纯科","mobile":"15702327945","imsi":"null","realMobile":"","address":"建设村和平社","workerId":null,"areaId":1190,"headUrl":"wansheng/wandongzhen/yangchunke.jpg","isMonitor":2,"village":null,"birthday":"","culture":"4","polics":"4","work":"","nation":"汉","operation":null,"locationLat":null,"locationLon":null,"locationTime":null,"onlineStatus":null,"tabDisastersInfo":[{"tabArea":null,"id":null,"disNo":null,"disName":"王爷庙不稳定斜坡","disType":null,"disState":null,"disLocation":"建设村和平社","disLon":106.8911,"disLat":28.9141,"disX":null,"disY":null,"disCause":null,"disTime":null,"disStratum":null,"disSurfaceType":null,"disSlope":null,"disArea":null,"disVolume":null,"disBefore":null,"disAfter":null,"imperilFamilies":null,"imperilMan":null,"imperilHouse":null,"imperilArea":null,"mainString":"居民","imperilMoney":null,"stableLevel":null,"imperilLevel":null,"dealIdea":null,"defenseLevel":null,"areaId":null,"qcqfryId":null,"warnMobile":null,"hasMobile":null,"disRadius":null,"village":null,"bulu":null,"groupNo":null,"scale":null,"stateTime":null,"comeTime":null,"operation":null,"statusNo":null,"videoName":null,"remark":null}],"tabArea":{"id":null,"areaParent":null,"unitId":null,"areaCode":null,"lon":null,"lat":null,"areaName":"万东镇"},"tabUnitMeteor":{"id":null,"unitName":"万东镇政府","unitParent":null,"unitLevel":null}}
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
         * id : 14091
         * name : 杨纯科
         * mobile : 15702327945
         * imsi : null
         * realMobile :
         * address : 建设村和平社
         * workerId : null
         * areaId : 1190
         * headUrl : wansheng/wandongzhen/yangchunke.jpg
         * isMonitor : 2
         * village : null
         * birthday :
         * culture : 4
         * polics : 4
         * work :
         * nation : 汉
         * operation : null
         * locationLat : null
         * locationLon : null
         * locationTime : null
         * onlineStatus : null
         * tabDisastersInfo : [{"tabArea":null,"id":null,"disNo":null,"disName":"王爷庙不稳定斜坡","disType":null,"disState":null,"disLocation":"建设村和平社","disLon":106.8911,"disLat":28.9141,"disX":null,"disY":null,"disCause":null,"disTime":null,"disStratum":null,"disSurfaceType":null,"disSlope":null,"disArea":null,"disVolume":null,"disBefore":null,"disAfter":null,"imperilFamilies":null,"imperilMan":null,"imperilHouse":null,"imperilArea":null,"mainString":"居民","imperilMoney":null,"stableLevel":null,"imperilLevel":null,"dealIdea":null,"defenseLevel":null,"areaId":null,"qcqfryId":null,"warnMobile":null,"hasMobile":null,"disRadius":null,"village":null,"bulu":null,"groupNo":null,"scale":null,"stateTime":null,"comeTime":null,"operation":null,"statusNo":null,"videoName":null,"remark":null}]
         * tabArea : {"id":null,"areaParent":null,"unitId":null,"areaCode":null,"lon":null,"lat":null,"areaName":"万东镇"}
         * tabUnitMeteor : {"id":null,"unitName":"万东镇政府","unitParent":null,"unitLevel":null}
         */

        private int id;
        private String name;
        private String mobile;
        private String imsi;
        private String realMobile;
        private String address;
        private String workerId;
        private int areaId;
        private String headUrl;
        private int isMonitor;
        private String village;
        private String birthday;
        private String culture;
        private String polics;
        private String work;
        private String nation;
        private String operation;
        private String locationLat;
        private String locationLon;
        private String locationTime;
        private String onlineStatus;
        private TabAreaBean tabArea;
        private TabUnitMeteorBean tabUnitMeteor;
        private List<TabDisastersInfoBean> tabDisastersInfo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getImsi() {
            return imsi;
        }

        public void setImsi(String imsi) {
            this.imsi = imsi;
        }

        public String getRealMobile() {
            return realMobile;
        }

        public void setRealMobile(String realMobile) {
            this.realMobile = realMobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getWorkerId() {
            return workerId;
        }

        public void setWorkerId(String workerId) {
            this.workerId = workerId;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getIsMonitor() {
            return isMonitor;
        }

        public void setIsMonitor(int isMonitor) {
            this.isMonitor = isMonitor;
        }

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCulture() {
            return culture;
        }

        public void setCulture(String culture) {
            this.culture = culture;
        }

        public String getPolics() {
            return polics;
        }

        public void setPolics(String polics) {
            this.polics = polics;
        }

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getLocationLat() {
            return locationLat;
        }

        public void setLocationLat(String locationLat) {
            this.locationLat = locationLat;
        }

        public String getLocationLon() {
            return locationLon;
        }

        public void setLocationLon(String locationLon) {
            this.locationLon = locationLon;
        }

        public String getLocationTime() {
            return locationTime;
        }

        public void setLocationTime(String locationTime) {
            this.locationTime = locationTime;
        }

        public String getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(String onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public TabAreaBean getTabArea() {
            return tabArea;
        }

        public void setTabArea(TabAreaBean tabArea) {
            this.tabArea = tabArea;
        }

        public TabUnitMeteorBean getTabUnitMeteor() {
            return tabUnitMeteor;
        }

        public void setTabUnitMeteor(TabUnitMeteorBean tabUnitMeteor) {
            this.tabUnitMeteor = tabUnitMeteor;
        }

        public List<TabDisastersInfoBean> getTabDisastersInfo() {
            return tabDisastersInfo;
        }

        public void setTabDisastersInfo(List<TabDisastersInfoBean> tabDisastersInfo) {
            this.tabDisastersInfo = tabDisastersInfo;
        }

        public static class TabAreaBean {
            /**
             * id : null
             * areaParent : null
             * unitId : null
             * areaCode : null
             * lon : null
             * lat : null
             * areaName : 万东镇
             */

            private String id;
            private String areaParent;
            private String unitId;
            private String areaCode;
            private String lon;
            private String lat;
            private String areaName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAreaParent() {
                return areaParent;
            }

            public void setAreaParent(String areaParent) {
                this.areaParent = areaParent;
            }

            public String getUnitId() {
                return unitId;
            }

            public void setUnitId(String unitId) {
                this.unitId = unitId;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }
        }

        public static class TabUnitMeteorBean {
            /**
             * id : null
             * unitName : 万东镇政府
             * unitParent : null
             * unitLevel : null
             */

            private String id;
            private String unitName;
            private String unitParent;
            private String unitLevel;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUnitName() {
                return unitName;
            }

            public void setUnitName(String unitName) {
                this.unitName = unitName;
            }

            public String getUnitParent() {
                return unitParent;
            }

            public void setUnitParent(String unitParent) {
                this.unitParent = unitParent;
            }

            public String getUnitLevel() {
                return unitLevel;
            }

            public void setUnitLevel(String unitLevel) {
                this.unitLevel = unitLevel;
            }
        }

        public static class TabDisastersInfoBean {
            /**
             * tabArea : null
             * id : null
             * disNo : null
             * disName : 王爷庙不稳定斜坡
             * disType : null
             * disState : null
             * disLocation : 建设村和平社
             * disLon : 106.8911
             * disLat : 28.9141
             * disX : null
             * disY : null
             * disCause : null
             * disTime : null
             * disStratum : null
             * disSurfaceType : null
             * disSlope : null
             * disArea : null
             * disVolume : null
             * disBefore : null
             * disAfter : null
             * imperilFamilies : null
             * imperilMan : null
             * imperilHouse : null
             * imperilArea : null
             * mainString : 居民
             * imperilMoney : null
             * stableLevel : null
             * imperilLevel : null
             * dealIdea : null
             * defenseLevel : null
             * areaId : null
             * qcqfryId : null
             * warnMobile : null
             * hasMobile : null
             * disRadius : null
             * village : null
             * bulu : null
             * groupNo : null
             * scale : null
             * stateTime : null
             * comeTime : null
             * operation : null
             * statusNo : null
             * videoName : null
             * remark : null
             */

            private String tabArea;
            private String id;
            private String disNo;
            private String disName;
            private String disType;
            private String disState;
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
            private String imperilFamilies;
            private String imperilMan;
            private String imperilHouse;
            private String imperilArea;
            private String mainString;
            private String imperilMoney;
            private String stableLevel;
            private String imperilLevel;
            private String dealIdea;
            private String defenseLevel;
            private String areaId;
            private String qcqfryId;
            private String warnMobile;
            private String hasMobile;
            private String disRadius;
            private String village;
            private String bulu;
            private String groupNo;
            private String scale;
            private String stateTime;
            private String comeTime;
            private String operation;
            private String statusNo;
            private String videoName;
            private String remark;

            public String getTabArea() {
                return tabArea;
            }

            public void setTabArea(String tabArea) {
                this.tabArea = tabArea;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
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

            public String getDisType() {
                return disType;
            }

            public void setDisType(String disType) {
                this.disType = disType;
            }

            public String getDisState() {
                return disState;
            }

            public void setDisState(String disState) {
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

            public String getImperilFamilies() {
                return imperilFamilies;
            }

            public void setImperilFamilies(String imperilFamilies) {
                this.imperilFamilies = imperilFamilies;
            }

            public String getImperilMan() {
                return imperilMan;
            }

            public void setImperilMan(String imperilMan) {
                this.imperilMan = imperilMan;
            }

            public String getImperilHouse() {
                return imperilHouse;
            }

            public void setImperilHouse(String imperilHouse) {
                this.imperilHouse = imperilHouse;
            }

            public String getImperilArea() {
                return imperilArea;
            }

            public void setImperilArea(String imperilArea) {
                this.imperilArea = imperilArea;
            }

            public String getMainString() {
                return mainString;
            }

            public void setMainString(String mainString) {
                this.mainString = mainString;
            }

            public String getImperilMoney() {
                return imperilMoney;
            }

            public void setImperilMoney(String imperilMoney) {
                this.imperilMoney = imperilMoney;
            }

            public String getStableLevel() {
                return stableLevel;
            }

            public void setStableLevel(String stableLevel) {
                this.stableLevel = stableLevel;
            }

            public String getImperilLevel() {
                return imperilLevel;
            }

            public void setImperilLevel(String imperilLevel) {
                this.imperilLevel = imperilLevel;
            }

            public String getDealIdea() {
                return dealIdea;
            }

            public void setDealIdea(String dealIdea) {
                this.dealIdea = dealIdea;
            }

            public String getDefenseLevel() {
                return defenseLevel;
            }

            public void setDefenseLevel(String defenseLevel) {
                this.defenseLevel = defenseLevel;
            }

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public String getQcqfryId() {
                return qcqfryId;
            }

            public void setQcqfryId(String qcqfryId) {
                this.qcqfryId = qcqfryId;
            }

            public String getWarnMobile() {
                return warnMobile;
            }

            public void setWarnMobile(String warnMobile) {
                this.warnMobile = warnMobile;
            }

            public String getHasMobile() {
                return hasMobile;
            }

            public void setHasMobile(String hasMobile) {
                this.hasMobile = hasMobile;
            }

            public String getDisRadius() {
                return disRadius;
            }

            public void setDisRadius(String disRadius) {
                this.disRadius = disRadius;
            }

            public String getVillage() {
                return village;
            }

            public void setVillage(String village) {
                this.village = village;
            }

            public String getBulu() {
                return bulu;
            }

            public void setBulu(String bulu) {
                this.bulu = bulu;
            }

            public String getGroupNo() {
                return groupNo;
            }

            public void setGroupNo(String groupNo) {
                this.groupNo = groupNo;
            }

            public String getScale() {
                return scale;
            }

            public void setScale(String scale) {
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

            public String getOperation() {
                return operation;
            }

            public void setOperation(String operation) {
                this.operation = operation;
            }

            public String getStatusNo() {
                return statusNo;
            }

            public void setStatusNo(String statusNo) {
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
        }
    }
}
