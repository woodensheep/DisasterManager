package com.nandi.disastermanager.entity;

import java.util.List;

/**
 * Created by lemon on 2017/8/15.
 */



public class KuangxuanInfo {


    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"disaster":[{"tabArea":{"id":18073,"areaParent":null,"unitId":null,"areaCode":500235121,"lon":null,"lat":null,"areaName":null},"id":18073,"disNo":"5002351210040101","disName":"锅龙背滑坡","disType":1,"disState":1,"disLocation":"重庆市云阳县黄石镇三田村 2、3组","disLon":108.70854192,"disLat":30.99521591,"disX":"3432700.2403","disY":"19281117.3543","disCause":"77","disTime":"","disStratum":"J2s","disSurfaceType":"29","disSlope":"25","disArea":"7.8","disVolume":"50.7","disBefore":"245","disAfter":"347","imperilFamilies":8,"imperilMan":30,"imperilHouse":35,"imperilArea":"1120","mainObject":"居民","imperilMoney":"130","stableLevel":31,"imperilLevel":19,"dealIdea":"$群测群防$","defenseLevel":40,"areaId":155,"qcqfryId":4442,"warnMobile":"15702311814","hasMobile":1,"disRadius":2,"village":"三田村","bulu":1,"groupNo":"2、3组","scale":18,"stateTime":"","comeTime":"","operation":1,"statusNo":0,"videoName":"","remark":null}],"havaOver":[],"efctNum":[{"imperil_man":30,"imperil_money":130,"imperil_families":8,"imperil_house":35,"imperil_area":1120}],"areaAdmin":[{"area_location":"青龙街道","id":1046,"area_id":151,"lon":108.717308,"real_mobile":"13101021678","area_name":"青龙街道","admin_pic":"yunyang/yufeng.jpg","admin_name":"余峰","telephone":"15702311692","lat":30.931176}],"havaKilled":[{"scale":19,"stable_level":43,"main_object":"","area_id":159,"has_mobile":1,"dis_before":"","dis_after":"","remark":"null","dis_cause":"","imperil_house":0,"dis_location":"重庆市云阳县宝坪镇梅滩村11组","state_time":"42444","id":18413,"operation":1,"dis_stratum":"","deal_idea":"$群测群防$工程治理$","dis_no":"5002351270100101","imperil_families":0,"village":"null","imperil_level":19,"defense_level":41,"area_name":"宝坪镇","status_no":3,"dis_lat":30.86166667,"dis_area":"","dis_time":"","dis_state":0,"dis_y":"","bulu":1,"dis_name":"双湾村滑坡(薛家老屋滑坡)","dis_radius":2,"areaCode":500235127,"group_no":"null","come_time":"","dis_surface_type":"","dis_x":"","warn_mobile":"","dis_type":1,"imperil_man":0,"imperil_money":"0","video_name":"","dis_lon":108.82027778,"dis_slope":"","imperil_area":"0","dis_volume":""}],"disasEquip":[],"dihuanzhan":[{"id":87,"area_id":275,"zhibantel":"48291469","location":"万盛区国土资源和房屋管理局","area_name":"万盛区","tel":"48293862","name":"牟联清","job":"地环站站长","lng":"106.927437","iphone":"15213062108","url":"wansheng/mulianqing.jpg","lat":"28.96346"}],"areaProfessor":[{"id":432,"phone":"13594198572","area_id":154,"manage_area":"双江街道  黄石镇","area_parent":136,"disarea":"双江街道  黄石镇","lng":108.711147,"dispicture":"wangwei.jpg","telephone":"15702330743","disname":"王 伟","lat":30.942122}],"havaMoved":[{"scale":19,"stable_level":43,"main_object":"","area_id":159,"has_mobile":1,"dis_before":"","dis_after":"","remark":"null","dis_cause":"","imperil_house":0,"dis_location":"重庆市云阳县宝坪镇梅滩村11组","state_time":"42444","id":18413,"operation":1,"dis_stratum":"","deal_idea":"$群测群防$工程治理$","dis_no":"5002351270100101","imperil_families":0,"village":"null","imperil_level":19,"defense_level":41,"area_name":"宝坪镇","status_no":3,"dis_lat":30.86166667,"dis_area":"","dis_time":"","dis_state":0,"dis_y":"","bulu":1,"dis_name":"双湾村滑坡(薛家老屋滑坡)","dis_radius":2,"areaCode":500235127,"group_no":"null","come_time":"","dis_surface_type":"","dis_x":"","warn_mobile":"","dis_type":1,"imperil_man":0,"imperil_money":"0","video_name":"","dis_lon":108.82027778,"dis_slope":"","imperil_area":"0","dis_volume":""}],"LargeDisasEquip":[],"human":[{"head_url":"yunyang/huangshizhen/xiongdaozhi.jpg","work":"","birthday":"","area_id":155,"dis_name":"锅龙背滑坡","polics":"4","nation":"汉","village":"","dis_lon":108.70854192,"address":"黄石镇","real_mobile":"15215224661","area_name":"黄石镇","name":"熊道植","dis_id":18073,"dis_lat":30.99521591,"is_monitor":2,"culture":"4","mobile":"15702311808"}]}
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
        private List<DisasterBean> disaster;
        private List<?> havaOver;
        private List<EfctNumBean> efctNum;
        private List<AreaAdminBean> areaAdmin;
        private List<HavaKilledBean> havaKilled;
        private List<?> disasEquip;
        private List<DihuanzhanBean> dihuanzhan;
        private List<AreaProfessorBean> areaProfessor;
        private List<HavaMovedBean> havaMoved;
        private List<?> LargeDisasEquip;
        private List<HumanBean> human;

        public List<DisasterBean> getDisaster() {
            return disaster;
        }

        public void setDisaster(List<DisasterBean> disaster) {
            this.disaster = disaster;
        }

        public List<?> getHavaOver() {
            return havaOver;
        }

        public void setHavaOver(List<?> havaOver) {
            this.havaOver = havaOver;
        }

        public List<EfctNumBean> getEfctNum() {
            return efctNum;
        }

        public void setEfctNum(List<EfctNumBean> efctNum) {
            this.efctNum = efctNum;
        }

        public List<AreaAdminBean> getAreaAdmin() {
            return areaAdmin;
        }

        public void setAreaAdmin(List<AreaAdminBean> areaAdmin) {
            this.areaAdmin = areaAdmin;
        }

        public List<HavaKilledBean> getHavaKilled() {
            return havaKilled;
        }

        public void setHavaKilled(List<HavaKilledBean> havaKilled) {
            this.havaKilled = havaKilled;
        }

        public List<?> getDisasEquip() {
            return disasEquip;
        }

        public void setDisasEquip(List<?> disasEquip) {
            this.disasEquip = disasEquip;
        }

        public List<DihuanzhanBean> getDihuanzhan() {
            return dihuanzhan;
        }

        public void setDihuanzhan(List<DihuanzhanBean> dihuanzhan) {
            this.dihuanzhan = dihuanzhan;
        }

        public List<AreaProfessorBean> getAreaProfessor() {
            return areaProfessor;
        }

        public void setAreaProfessor(List<AreaProfessorBean> areaProfessor) {
            this.areaProfessor = areaProfessor;
        }

        public List<HavaMovedBean> getHavaMoved() {
            return havaMoved;
        }

        public void setHavaMoved(List<HavaMovedBean> havaMoved) {
            this.havaMoved = havaMoved;
        }

        public List<?> getLargeDisasEquip() {
            return LargeDisasEquip;
        }

        public void setLargeDisasEquip(List<?> LargeDisasEquip) {
            this.LargeDisasEquip = LargeDisasEquip;
        }

        public List<HumanBean> getHuman() {
            return human;
        }

        public void setHuman(List<HumanBean> human) {
            this.human = human;
        }

        public static class DisasterBean {
            /**
             * tabArea : {"id":18073,"areaParent":null,"unitId":null,"areaCode":500235121,"lon":null,"lat":null,"areaName":null}
             * id : 18073
             * disNo : 5002351210040101
             * disName : 锅龙背滑坡
             * disType : 1
             * disState : 1
             * disLocation : 重庆市云阳县黄石镇三田村 2、3组
             * disLon : 108.70854192
             * disLat : 30.99521591
             * disX : 3432700.2403
             * disY : 19281117.3543
             * disCause : 77
             * disTime :
             * disStratum : J2s
             * disSurfaceType : 29
             * disSlope : 25
             * disArea : 7.8
             * disVolume : 50.7
             * disBefore : 245
             * disAfter : 347
             * imperilFamilies : 8
             * imperilMan : 30
             * imperilHouse : 35
             * imperilArea : 1120
             * mainObject : 居民
             * imperilMoney : 130
             * stableLevel : 31
             * imperilLevel : 19
             * dealIdea : $群测群防$
             * defenseLevel : 40
             * areaId : 155
             * qcqfryId : 4442
             * warnMobile : 15702311814
             * hasMobile : 1
             * disRadius : 2
             * village : 三田村
             * bulu : 1
             * groupNo : 2、3组
             * scale : 18
             * stateTime :
             * comeTime :
             * operation : 1
             * statusNo : 0
             * videoName :
             * remark : null
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
            private Object remark;

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

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public static class TabAreaBean {
                /**
                 * id : 18073
                 * areaParent : null
                 * unitId : null
                 * areaCode : 500235121
                 * lon : null
                 * lat : null
                 * areaName : null
                 */

                private int id;
                private Object areaParent;
                private Object unitId;
                private int areaCode;
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

                public int getAreaCode() {
                    return areaCode;
                }

                public void setAreaCode(int areaCode) {
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

        public static class EfctNumBean {
            /**
             * imperil_man : 30
             * imperil_money : 130
             * imperil_families : 8
             * imperil_house : 35
             * imperil_area : 1120
             */

            private int imperil_man;
            private int imperil_money;
            private int imperil_families;
            private int imperil_house;
            private int imperil_area;

            public int getImperil_man() {
                return imperil_man;
            }

            public void setImperil_man(int imperil_man) {
                this.imperil_man = imperil_man;
            }

            public int getImperil_money() {
                return imperil_money;
            }

            public void setImperil_money(int imperil_money) {
                this.imperil_money = imperil_money;
            }

            public int getImperil_families() {
                return imperil_families;
            }

            public void setImperil_families(int imperil_families) {
                this.imperil_families = imperil_families;
            }

            public int getImperil_house() {
                return imperil_house;
            }

            public void setImperil_house(int imperil_house) {
                this.imperil_house = imperil_house;
            }

            public int getImperil_area() {
                return imperil_area;
            }

            public void setImperil_area(int imperil_area) {
                this.imperil_area = imperil_area;
            }
        }

        public static class AreaAdminBean {
            /**
             * area_location : 青龙街道
             * id : 1046
             * area_id : 151
             * lon : 108.717308
             * real_mobile : 13101021678
             * area_name : 青龙街道
             * admin_pic : yunyang/yufeng.jpg
             * admin_name : 余峰
             * telephone : 15702311692
             * lat : 30.931176
             */

            private String area_location;
            private int id;
            private int area_id;
            private double lon;
            private String real_mobile;
            private String area_name;
            private String admin_pic;
            private String admin_name;
            private String telephone;
            private double lat;

            public String getArea_location() {
                return area_location;
            }

            public void setArea_location(String area_location) {
                this.area_location = area_location;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public String getReal_mobile() {
                return real_mobile;
            }

            public void setReal_mobile(String real_mobile) {
                this.real_mobile = real_mobile;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public String getAdmin_pic() {
                return admin_pic;
            }

            public void setAdmin_pic(String admin_pic) {
                this.admin_pic = admin_pic;
            }

            public String getAdmin_name() {
                return admin_name;
            }

            public void setAdmin_name(String admin_name) {
                this.admin_name = admin_name;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }

        public static class HavaKilledBean {
            /**
             * scale : 19
             * stable_level : 43
             * main_object :
             * area_id : 159
             * has_mobile : 1
             * dis_before :
             * dis_after :
             * remark : null
             * dis_cause :
             * imperil_house : 0
             * dis_location : 重庆市云阳县宝坪镇梅滩村11组
             * state_time : 42444
             * id : 18413
             * operation : 1
             * dis_stratum :
             * deal_idea : $群测群防$工程治理$
             * dis_no : 5002351270100101
             * imperil_families : 0
             * village : null
             * imperil_level : 19
             * defense_level : 41
             * area_name : 宝坪镇
             * status_no : 3
             * dis_lat : 30.86166667
             * dis_area :
             * dis_time :
             * dis_state : 0
             * dis_y :
             * bulu : 1
             * dis_name : 双湾村滑坡(薛家老屋滑坡)
             * dis_radius : 2
             * areaCode : 500235127
             * group_no : null
             * come_time :
             * dis_surface_type :
             * dis_x :
             * warn_mobile :
             * dis_type : 1
             * imperil_man : 0
             * imperil_money : 0
             * video_name :
             * dis_lon : 108.82027778
             * dis_slope :
             * imperil_area : 0
             * dis_volume :
             */

            private int scale;
            private int stable_level;
            private String main_object;
            private int area_id;
            private int has_mobile;
            private String dis_before;
            private String dis_after;
            private String remark;
            private String dis_cause;
            private int imperil_house;
            private String dis_location;
            private String state_time;
            private int id;
            private int operation;
            private String dis_stratum;
            private String deal_idea;
            private String dis_no;
            private int imperil_families;
            private String village;
            private int imperil_level;
            private int defense_level;
            private String area_name;
            private int status_no;
            private double dis_lat;
            private String dis_area;
            private String dis_time;
            private int dis_state;
            private String dis_y;
            private int bulu;
            private String dis_name;
            private int dis_radius;
            private int areaCode;
            private String group_no;
            private String come_time;
            private String dis_surface_type;
            private String dis_x;
            private String warn_mobile;
            private int dis_type;
            private int imperil_man;
            private String imperil_money;
            private String video_name;
            private double dis_lon;
            private String dis_slope;
            private String imperil_area;
            private String dis_volume;

            public int getScale() {
                return scale;
            }

            public void setScale(int scale) {
                this.scale = scale;
            }

            public int getStable_level() {
                return stable_level;
            }

            public void setStable_level(int stable_level) {
                this.stable_level = stable_level;
            }

            public String getMain_object() {
                return main_object;
            }

            public void setMain_object(String main_object) {
                this.main_object = main_object;
            }

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public int getHas_mobile() {
                return has_mobile;
            }

            public void setHas_mobile(int has_mobile) {
                this.has_mobile = has_mobile;
            }

            public String getDis_before() {
                return dis_before;
            }

            public void setDis_before(String dis_before) {
                this.dis_before = dis_before;
            }

            public String getDis_after() {
                return dis_after;
            }

            public void setDis_after(String dis_after) {
                this.dis_after = dis_after;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getDis_cause() {
                return dis_cause;
            }

            public void setDis_cause(String dis_cause) {
                this.dis_cause = dis_cause;
            }

            public int getImperil_house() {
                return imperil_house;
            }

            public void setImperil_house(int imperil_house) {
                this.imperil_house = imperil_house;
            }

            public String getDis_location() {
                return dis_location;
            }

            public void setDis_location(String dis_location) {
                this.dis_location = dis_location;
            }

            public String getState_time() {
                return state_time;
            }

            public void setState_time(String state_time) {
                this.state_time = state_time;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOperation() {
                return operation;
            }

            public void setOperation(int operation) {
                this.operation = operation;
            }

            public String getDis_stratum() {
                return dis_stratum;
            }

            public void setDis_stratum(String dis_stratum) {
                this.dis_stratum = dis_stratum;
            }

            public String getDeal_idea() {
                return deal_idea;
            }

            public void setDeal_idea(String deal_idea) {
                this.deal_idea = deal_idea;
            }

            public String getDis_no() {
                return dis_no;
            }

            public void setDis_no(String dis_no) {
                this.dis_no = dis_no;
            }

            public int getImperil_families() {
                return imperil_families;
            }

            public void setImperil_families(int imperil_families) {
                this.imperil_families = imperil_families;
            }

            public String getVillage() {
                return village;
            }

            public void setVillage(String village) {
                this.village = village;
            }

            public int getImperil_level() {
                return imperil_level;
            }

            public void setImperil_level(int imperil_level) {
                this.imperil_level = imperil_level;
            }

            public int getDefense_level() {
                return defense_level;
            }

            public void setDefense_level(int defense_level) {
                this.defense_level = defense_level;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public int getStatus_no() {
                return status_no;
            }

            public void setStatus_no(int status_no) {
                this.status_no = status_no;
            }

            public double getDis_lat() {
                return dis_lat;
            }

            public void setDis_lat(double dis_lat) {
                this.dis_lat = dis_lat;
            }

            public String getDis_area() {
                return dis_area;
            }

            public void setDis_area(String dis_area) {
                this.dis_area = dis_area;
            }

            public String getDis_time() {
                return dis_time;
            }

            public void setDis_time(String dis_time) {
                this.dis_time = dis_time;
            }

            public int getDis_state() {
                return dis_state;
            }

            public void setDis_state(int dis_state) {
                this.dis_state = dis_state;
            }

            public String getDis_y() {
                return dis_y;
            }

            public void setDis_y(String dis_y) {
                this.dis_y = dis_y;
            }

            public int getBulu() {
                return bulu;
            }

            public void setBulu(int bulu) {
                this.bulu = bulu;
            }

            public String getDis_name() {
                return dis_name;
            }

            public void setDis_name(String dis_name) {
                this.dis_name = dis_name;
            }

            public int getDis_radius() {
                return dis_radius;
            }

            public void setDis_radius(int dis_radius) {
                this.dis_radius = dis_radius;
            }

            public int getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(int areaCode) {
                this.areaCode = areaCode;
            }

            public String getGroup_no() {
                return group_no;
            }

            public void setGroup_no(String group_no) {
                this.group_no = group_no;
            }

            public String getCome_time() {
                return come_time;
            }

            public void setCome_time(String come_time) {
                this.come_time = come_time;
            }

            public String getDis_surface_type() {
                return dis_surface_type;
            }

            public void setDis_surface_type(String dis_surface_type) {
                this.dis_surface_type = dis_surface_type;
            }

            public String getDis_x() {
                return dis_x;
            }

            public void setDis_x(String dis_x) {
                this.dis_x = dis_x;
            }

            public String getWarn_mobile() {
                return warn_mobile;
            }

            public void setWarn_mobile(String warn_mobile) {
                this.warn_mobile = warn_mobile;
            }

            public int getDis_type() {
                return dis_type;
            }

            public void setDis_type(int dis_type) {
                this.dis_type = dis_type;
            }

            public int getImperil_man() {
                return imperil_man;
            }

            public void setImperil_man(int imperil_man) {
                this.imperil_man = imperil_man;
            }

            public String getImperil_money() {
                return imperil_money;
            }

            public void setImperil_money(String imperil_money) {
                this.imperil_money = imperil_money;
            }

            public String getVideo_name() {
                return video_name;
            }

            public void setVideo_name(String video_name) {
                this.video_name = video_name;
            }

            public double getDis_lon() {
                return dis_lon;
            }

            public void setDis_lon(double dis_lon) {
                this.dis_lon = dis_lon;
            }

            public String getDis_slope() {
                return dis_slope;
            }

            public void setDis_slope(String dis_slope) {
                this.dis_slope = dis_slope;
            }

            public String getImperil_area() {
                return imperil_area;
            }

            public void setImperil_area(String imperil_area) {
                this.imperil_area = imperil_area;
            }

            public String getDis_volume() {
                return dis_volume;
            }

            public void setDis_volume(String dis_volume) {
                this.dis_volume = dis_volume;
            }
        }

        public static class DihuanzhanBean {
            /**
             * id : 87
             * area_id : 275
             * zhibantel : 48291469
             * location : 万盛区国土资源和房屋管理局
             * area_name : 万盛区
             * tel : 48293862
             * name : 牟联清
             * job : 地环站站长
             * lng : 106.927437
             * iphone : 15213062108
             * url : wansheng/mulianqing.jpg
             * lat : 28.96346
             */

            private int id;
            private int area_id;
            private String zhibantel;
            private String location;
            private String area_name;
            private String tel;
            private String name;
            private String job;
            private String lng;
            private String iphone;
            private String url;
            private String lat;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getZhibantel() {
                return zhibantel;
            }

            public void setZhibantel(String zhibantel) {
                this.zhibantel = zhibantel;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getJob() {
                return job;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getIphone() {
                return iphone;
            }

            public void setIphone(String iphone) {
                this.iphone = iphone;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }
        }

        public static class AreaProfessorBean {
            /**
             * id : 432
             * phone : 13594198572
             * area_id : 154
             * manage_area : 双江街道  黄石镇
             * area_parent : 136
             * disarea : 双江街道  黄石镇
             * lng : 108.711147
             * dispicture : wangwei.jpg
             * telephone : 15702330743
             * disname : 王 伟
             * lat : 30.942122
             */

            private int id;
            private String phone;
            private int area_id;
            private String manage_area;
            private int area_parent;
            private String disarea;
            private double lng;
            private String dispicture;
            private String telephone;
            private String disname;
            private double lat;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getManage_area() {
                return manage_area;
            }

            public void setManage_area(String manage_area) {
                this.manage_area = manage_area;
            }

            public int getArea_parent() {
                return area_parent;
            }

            public void setArea_parent(int area_parent) {
                this.area_parent = area_parent;
            }

            public String getDisarea() {
                return disarea;
            }

            public void setDisarea(String disarea) {
                this.disarea = disarea;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public String getDispicture() {
                return dispicture;
            }

            public void setDispicture(String dispicture) {
                this.dispicture = dispicture;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getDisname() {
                return disname;
            }

            public void setDisname(String disname) {
                this.disname = disname;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }

        public static class HavaMovedBean {
            /**
             * scale : 19
             * stable_level : 43
             * main_object :
             * area_id : 159
             * has_mobile : 1
             * dis_before :
             * dis_after :
             * remark : null
             * dis_cause :
             * imperil_house : 0
             * dis_location : 重庆市云阳县宝坪镇梅滩村11组
             * state_time : 42444
             * id : 18413
             * operation : 1
             * dis_stratum :
             * deal_idea : $群测群防$工程治理$
             * dis_no : 5002351270100101
             * imperil_families : 0
             * village : null
             * imperil_level : 19
             * defense_level : 41
             * area_name : 宝坪镇
             * status_no : 3
             * dis_lat : 30.86166667
             * dis_area :
             * dis_time :
             * dis_state : 0
             * dis_y :
             * bulu : 1
             * dis_name : 双湾村滑坡(薛家老屋滑坡)
             * dis_radius : 2
             * areaCode : 500235127
             * group_no : null
             * come_time :
             * dis_surface_type :
             * dis_x :
             * warn_mobile :
             * dis_type : 1
             * imperil_man : 0
             * imperil_money : 0
             * video_name :
             * dis_lon : 108.82027778
             * dis_slope :
             * imperil_area : 0
             * dis_volume :
             */

            private int scale;
            private int stable_level;
            private String main_object;
            private int area_id;
            private int has_mobile;
            private String dis_before;
            private String dis_after;
            private String remark;
            private String dis_cause;
            private int imperil_house;
            private String dis_location;
            private String state_time;
            private int id;
            private int operation;
            private String dis_stratum;
            private String deal_idea;
            private String dis_no;
            private int imperil_families;
            private String village;
            private int imperil_level;
            private int defense_level;
            private String area_name;
            private int status_no;
            private double dis_lat;
            private String dis_area;
            private String dis_time;
            private int dis_state;
            private String dis_y;
            private int bulu;
            private String dis_name;
            private int dis_radius;
            private int areaCode;
            private String group_no;
            private String come_time;
            private String dis_surface_type;
            private String dis_x;
            private String warn_mobile;
            private int dis_type;
            private int imperil_man;
            private String imperil_money;
            private String video_name;
            private double dis_lon;
            private String dis_slope;
            private String imperil_area;
            private String dis_volume;

            public int getScale() {
                return scale;
            }

            public void setScale(int scale) {
                this.scale = scale;
            }

            public int getStable_level() {
                return stable_level;
            }

            public void setStable_level(int stable_level) {
                this.stable_level = stable_level;
            }

            public String getMain_object() {
                return main_object;
            }

            public void setMain_object(String main_object) {
                this.main_object = main_object;
            }

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public int getHas_mobile() {
                return has_mobile;
            }

            public void setHas_mobile(int has_mobile) {
                this.has_mobile = has_mobile;
            }

            public String getDis_before() {
                return dis_before;
            }

            public void setDis_before(String dis_before) {
                this.dis_before = dis_before;
            }

            public String getDis_after() {
                return dis_after;
            }

            public void setDis_after(String dis_after) {
                this.dis_after = dis_after;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getDis_cause() {
                return dis_cause;
            }

            public void setDis_cause(String dis_cause) {
                this.dis_cause = dis_cause;
            }

            public int getImperil_house() {
                return imperil_house;
            }

            public void setImperil_house(int imperil_house) {
                this.imperil_house = imperil_house;
            }

            public String getDis_location() {
                return dis_location;
            }

            public void setDis_location(String dis_location) {
                this.dis_location = dis_location;
            }

            public String getState_time() {
                return state_time;
            }

            public void setState_time(String state_time) {
                this.state_time = state_time;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOperation() {
                return operation;
            }

            public void setOperation(int operation) {
                this.operation = operation;
            }

            public String getDis_stratum() {
                return dis_stratum;
            }

            public void setDis_stratum(String dis_stratum) {
                this.dis_stratum = dis_stratum;
            }

            public String getDeal_idea() {
                return deal_idea;
            }

            public void setDeal_idea(String deal_idea) {
                this.deal_idea = deal_idea;
            }

            public String getDis_no() {
                return dis_no;
            }

            public void setDis_no(String dis_no) {
                this.dis_no = dis_no;
            }

            public int getImperil_families() {
                return imperil_families;
            }

            public void setImperil_families(int imperil_families) {
                this.imperil_families = imperil_families;
            }

            public String getVillage() {
                return village;
            }

            public void setVillage(String village) {
                this.village = village;
            }

            public int getImperil_level() {
                return imperil_level;
            }

            public void setImperil_level(int imperil_level) {
                this.imperil_level = imperil_level;
            }

            public int getDefense_level() {
                return defense_level;
            }

            public void setDefense_level(int defense_level) {
                this.defense_level = defense_level;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public int getStatus_no() {
                return status_no;
            }

            public void setStatus_no(int status_no) {
                this.status_no = status_no;
            }

            public double getDis_lat() {
                return dis_lat;
            }

            public void setDis_lat(double dis_lat) {
                this.dis_lat = dis_lat;
            }

            public String getDis_area() {
                return dis_area;
            }

            public void setDis_area(String dis_area) {
                this.dis_area = dis_area;
            }

            public String getDis_time() {
                return dis_time;
            }

            public void setDis_time(String dis_time) {
                this.dis_time = dis_time;
            }

            public int getDis_state() {
                return dis_state;
            }

            public void setDis_state(int dis_state) {
                this.dis_state = dis_state;
            }

            public String getDis_y() {
                return dis_y;
            }

            public void setDis_y(String dis_y) {
                this.dis_y = dis_y;
            }

            public int getBulu() {
                return bulu;
            }

            public void setBulu(int bulu) {
                this.bulu = bulu;
            }

            public String getDis_name() {
                return dis_name;
            }

            public void setDis_name(String dis_name) {
                this.dis_name = dis_name;
            }

            public int getDis_radius() {
                return dis_radius;
            }

            public void setDis_radius(int dis_radius) {
                this.dis_radius = dis_radius;
            }

            public int getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(int areaCode) {
                this.areaCode = areaCode;
            }

            public String getGroup_no() {
                return group_no;
            }

            public void setGroup_no(String group_no) {
                this.group_no = group_no;
            }

            public String getCome_time() {
                return come_time;
            }

            public void setCome_time(String come_time) {
                this.come_time = come_time;
            }

            public String getDis_surface_type() {
                return dis_surface_type;
            }

            public void setDis_surface_type(String dis_surface_type) {
                this.dis_surface_type = dis_surface_type;
            }

            public String getDis_x() {
                return dis_x;
            }

            public void setDis_x(String dis_x) {
                this.dis_x = dis_x;
            }

            public String getWarn_mobile() {
                return warn_mobile;
            }

            public void setWarn_mobile(String warn_mobile) {
                this.warn_mobile = warn_mobile;
            }

            public int getDis_type() {
                return dis_type;
            }

            public void setDis_type(int dis_type) {
                this.dis_type = dis_type;
            }

            public int getImperil_man() {
                return imperil_man;
            }

            public void setImperil_man(int imperil_man) {
                this.imperil_man = imperil_man;
            }

            public String getImperil_money() {
                return imperil_money;
            }

            public void setImperil_money(String imperil_money) {
                this.imperil_money = imperil_money;
            }

            public String getVideo_name() {
                return video_name;
            }

            public void setVideo_name(String video_name) {
                this.video_name = video_name;
            }

            public double getDis_lon() {
                return dis_lon;
            }

            public void setDis_lon(double dis_lon) {
                this.dis_lon = dis_lon;
            }

            public String getDis_slope() {
                return dis_slope;
            }

            public void setDis_slope(String dis_slope) {
                this.dis_slope = dis_slope;
            }

            public String getImperil_area() {
                return imperil_area;
            }

            public void setImperil_area(String imperil_area) {
                this.imperil_area = imperil_area;
            }

            public String getDis_volume() {
                return dis_volume;
            }

            public void setDis_volume(String dis_volume) {
                this.dis_volume = dis_volume;
            }
        }

        public static class HumanBean {
            /**
             * head_url : yunyang/huangshizhen/xiongdaozhi.jpg
             * work :
             * birthday :
             * area_id : 155
             * dis_name : 锅龙背滑坡
             * polics : 4
             * nation : 汉
             * village :
             * dis_lon : 108.70854192
             * address : 黄石镇
             * real_mobile : 15215224661
             * area_name : 黄石镇
             * name : 熊道植
             * dis_id : 18073
             * dis_lat : 30.99521591
             * is_monitor : 2
             * culture : 4
             * mobile : 15702311808
             */

            private String head_url;
            private String work;
            private String birthday;
            private int area_id;
            private String dis_name;
            private String polics;
            private String nation;
            private String village;
            private double dis_lon;
            private String address;
            private String real_mobile;
            private String area_name;
            private String name;
            private int dis_id;
            private double dis_lat;
            private int is_monitor;
            private String culture;
            private String mobile;

            public String getHead_url() {
                return head_url;
            }

            public void setHead_url(String head_url) {
                this.head_url = head_url;
            }

            public String getWork() {
                return work;
            }

            public void setWork(String work) {
                this.work = work;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getDis_name() {
                return dis_name;
            }

            public void setDis_name(String dis_name) {
                this.dis_name = dis_name;
            }

            public String getPolics() {
                return polics;
            }

            public void setPolics(String polics) {
                this.polics = polics;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getVillage() {
                return village;
            }

            public void setVillage(String village) {
                this.village = village;
            }

            public double getDis_lon() {
                return dis_lon;
            }

            public void setDis_lon(double dis_lon) {
                this.dis_lon = dis_lon;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getReal_mobile() {
                return real_mobile;
            }

            public void setReal_mobile(String real_mobile) {
                this.real_mobile = real_mobile;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getDis_id() {
                return dis_id;
            }

            public void setDis_id(int dis_id) {
                this.dis_id = dis_id;
            }

            public double getDis_lat() {
                return dis_lat;
            }

            public void setDis_lat(double dis_lat) {
                this.dis_lat = dis_lat;
            }

            public int getIs_monitor() {
                return is_monitor;
            }

            public void setIs_monitor(int is_monitor) {
                this.is_monitor = is_monitor;
            }

            public String getCulture() {
                return culture;
            }

            public void setCulture(String culture) {
                this.culture = culture;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }
    }
}
