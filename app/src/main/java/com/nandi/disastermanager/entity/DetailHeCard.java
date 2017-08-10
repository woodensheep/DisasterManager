package com.nandi.disastermanager.entity;

/**
 * Created by lemon on 2017/8/10.
 * 避险明白卡
 */

public class DetailHeCard {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"id":29722,"hPreSignal":"锣鼓 口哨 呼叫","hEvaPlacement":"村办公室","hAmbulanceUnit":"青年医院","hGrantUnit":"青年镇人民政府","hGrantDate":"2016/03/20","hHolder":"朱绍碧","hHolderPhone":"18736673079","hHolderDate":"2016/03/20","name1":"罗聪","name2":"杨久平","name3":"罗爱华","name4":"卢明福","name5":"卢诚嘉","name6":"卢一鸣","name7":"","sex1":"男","sex2":"女","sex3":"女","sex4":"男","sex5":"女","sex6":"女","sex7":"","name8":"","sex8":"","age1":64,"age2":62,"age3":40,"age4":42,"age5":13,"age6":9,"age7":null,"age8":null,"hNo":"5001101020010101","hFamilyName":"罗聪","hFamilyNum":"6","hHouseType":"砖混","hAddress":"重庆市万盛区青年镇毛里村关山沟组","hDisType":"滑坡","hDisScale":"100万平方","hDisRelationship":"滑坡地段内","hDisFactor":"大雨 暴雨","hDisMatters":"听到铜锣响按照明白卡指定方向撤离法监测","hPreMan":"朱绍碧","hPrePhone":"15310157270","hSignalMan":"朱绍碧","hSignalPhone":"15310157270","hEvaLine":"往马颈子方向","hPlacementMan":"郭昭平","hPlacementPhone":"13983763726","hAmbulanceMan":"刘兆银","hAmbulancePhone":"48351051","hGrantPhone":"48351067"}
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
         * id : 29722
         * hPreSignal : 锣鼓 口哨 呼叫
         * hEvaPlacement : 村办公室
         * hAmbulanceUnit : 青年医院
         * hGrantUnit : 青年镇人民政府
         * hGrantDate : 2016/03/20
         * hHolder : 朱绍碧
         * hHolderPhone : 18736673079
         * hHolderDate : 2016/03/20
         * name1 : 罗聪
         * name2 : 杨久平
         * name3 : 罗爱华
         * name4 : 卢明福
         * name5 : 卢诚嘉
         * name6 : 卢一鸣
         * name7 :
         * sex1 : 男
         * sex2 : 女
         * sex3 : 女
         * sex4 : 男
         * sex5 : 女
         * sex6 : 女
         * sex7 :
         * name8 :
         * sex8 :
         * age1 : 64
         * age2 : 62
         * age3 : 40
         * age4 : 42
         * age5 : 13
         * age6 : 9
         * age7 : null
         * age8 : null
         * hNo : 5001101020010101
         * hFamilyName : 罗聪
         * hFamilyNum : 6
         * hHouseType : 砖混
         * hAddress : 重庆市万盛区青年镇毛里村关山沟组
         * hDisType : 滑坡
         * hDisScale : 100万平方
         * hDisRelationship : 滑坡地段内
         * hDisFactor : 大雨 暴雨
         * hDisMatters : 听到铜锣响按照明白卡指定方向撤离法监测
         * hPreMan : 朱绍碧
         * hPrePhone : 15310157270
         * hSignalMan : 朱绍碧
         * hSignalPhone : 15310157270
         * hEvaLine : 往马颈子方向
         * hPlacementMan : 郭昭平
         * hPlacementPhone : 13983763726
         * hAmbulanceMan : 刘兆银
         * hAmbulancePhone : 48351051
         * hGrantPhone : 48351067
         */

        private int id;
        private String hPreSignal;
        private String hEvaPlacement;
        private String hAmbulanceUnit;
        private String hGrantUnit;
        private String hGrantDate;
        private String hHolder;
        private String hHolderPhone;
        private String hHolderDate;
        private String name1;
        private String name2;
        private String name3;
        private String name4;
        private String name5;
        private String name6;
        private String name7;
        private String sex1;
        private String sex2;
        private String sex3;
        private String sex4;
        private String sex5;
        private String sex6;
        private String sex7;
        private String name8;
        private String sex8;
        private int age1;
        private int age2;
        private int age3;
        private int age4;
        private int age5;
        private int age6;
        private Object age7;
        private Object age8;
        private String hNo;
        private String hFamilyName;
        private String hFamilyNum;
        private String hHouseType;
        private String hAddress;
        private String hDisType;
        private String hDisScale;
        private String hDisRelationship;
        private String hDisFactor;
        private String hDisMatters;
        private String hPreMan;
        private String hPrePhone;
        private String hSignalMan;
        private String hSignalPhone;
        private String hEvaLine;
        private String hPlacementMan;
        private String hPlacementPhone;
        private String hAmbulanceMan;
        private String hAmbulancePhone;
        private String hGrantPhone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHPreSignal() {
            return hPreSignal;
        }

        public void setHPreSignal(String hPreSignal) {
            this.hPreSignal = hPreSignal;
        }

        public String getHEvaPlacement() {
            return hEvaPlacement;
        }

        public void setHEvaPlacement(String hEvaPlacement) {
            this.hEvaPlacement = hEvaPlacement;
        }

        public String getHAmbulanceUnit() {
            return hAmbulanceUnit;
        }

        public void setHAmbulanceUnit(String hAmbulanceUnit) {
            this.hAmbulanceUnit = hAmbulanceUnit;
        }

        public String getHGrantUnit() {
            return hGrantUnit;
        }

        public void setHGrantUnit(String hGrantUnit) {
            this.hGrantUnit = hGrantUnit;
        }

        public String getHGrantDate() {
            return hGrantDate;
        }

        public void setHGrantDate(String hGrantDate) {
            this.hGrantDate = hGrantDate;
        }

        public String getHHolder() {
            return hHolder;
        }

        public void setHHolder(String hHolder) {
            this.hHolder = hHolder;
        }

        public String getHHolderPhone() {
            return hHolderPhone;
        }

        public void setHHolderPhone(String hHolderPhone) {
            this.hHolderPhone = hHolderPhone;
        }

        public String getHHolderDate() {
            return hHolderDate;
        }

        public void setHHolderDate(String hHolderDate) {
            this.hHolderDate = hHolderDate;
        }

        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public String getName2() {
            return name2;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public String getName3() {
            return name3;
        }

        public void setName3(String name3) {
            this.name3 = name3;
        }

        public String getName4() {
            return name4;
        }

        public void setName4(String name4) {
            this.name4 = name4;
        }

        public String getName5() {
            return name5;
        }

        public void setName5(String name5) {
            this.name5 = name5;
        }

        public String getName6() {
            return name6;
        }

        public void setName6(String name6) {
            this.name6 = name6;
        }

        public String getName7() {
            return name7;
        }

        public void setName7(String name7) {
            this.name7 = name7;
        }

        public String getSex1() {
            return sex1;
        }

        public void setSex1(String sex1) {
            this.sex1 = sex1;
        }

        public String getSex2() {
            return sex2;
        }

        public void setSex2(String sex2) {
            this.sex2 = sex2;
        }

        public String getSex3() {
            return sex3;
        }

        public void setSex3(String sex3) {
            this.sex3 = sex3;
        }

        public String getSex4() {
            return sex4;
        }

        public void setSex4(String sex4) {
            this.sex4 = sex4;
        }

        public String getSex5() {
            return sex5;
        }

        public void setSex5(String sex5) {
            this.sex5 = sex5;
        }

        public String getSex6() {
            return sex6;
        }

        public void setSex6(String sex6) {
            this.sex6 = sex6;
        }

        public String getSex7() {
            return sex7;
        }

        public void setSex7(String sex7) {
            this.sex7 = sex7;
        }

        public String getName8() {
            return name8;
        }

        public void setName8(String name8) {
            this.name8 = name8;
        }

        public String getSex8() {
            return sex8;
        }

        public void setSex8(String sex8) {
            this.sex8 = sex8;
        }

        public int getAge1() {
            return age1;
        }

        public void setAge1(int age1) {
            this.age1 = age1;
        }

        public int getAge2() {
            return age2;
        }

        public void setAge2(int age2) {
            this.age2 = age2;
        }

        public int getAge3() {
            return age3;
        }

        public void setAge3(int age3) {
            this.age3 = age3;
        }

        public int getAge4() {
            return age4;
        }

        public void setAge4(int age4) {
            this.age4 = age4;
        }

        public int getAge5() {
            return age5;
        }

        public void setAge5(int age5) {
            this.age5 = age5;
        }

        public int getAge6() {
            return age6;
        }

        public void setAge6(int age6) {
            this.age6 = age6;
        }

        public Object getAge7() {
            return age7;
        }

        public void setAge7(Object age7) {
            this.age7 = age7;
        }

        public Object getAge8() {
            return age8;
        }

        public void setAge8(Object age8) {
            this.age8 = age8;
        }

        public String getHNo() {
            return hNo;
        }

        public void setHNo(String hNo) {
            this.hNo = hNo;
        }

        public String getHFamilyName() {
            return hFamilyName;
        }

        public void setHFamilyName(String hFamilyName) {
            this.hFamilyName = hFamilyName;
        }

        public String getHFamilyNum() {
            return hFamilyNum;
        }

        public void setHFamilyNum(String hFamilyNum) {
            this.hFamilyNum = hFamilyNum;
        }

        public String getHHouseType() {
            return hHouseType;
        }

        public void setHHouseType(String hHouseType) {
            this.hHouseType = hHouseType;
        }

        public String getHAddress() {
            return hAddress;
        }

        public void setHAddress(String hAddress) {
            this.hAddress = hAddress;
        }

        public String getHDisType() {
            return hDisType;
        }

        public void setHDisType(String hDisType) {
            this.hDisType = hDisType;
        }

        public String getHDisScale() {
            return hDisScale;
        }

        public void setHDisScale(String hDisScale) {
            this.hDisScale = hDisScale;
        }

        public String getHDisRelationship() {
            return hDisRelationship;
        }

        public void setHDisRelationship(String hDisRelationship) {
            this.hDisRelationship = hDisRelationship;
        }

        public String getHDisFactor() {
            return hDisFactor;
        }

        public void setHDisFactor(String hDisFactor) {
            this.hDisFactor = hDisFactor;
        }

        public String getHDisMatters() {
            return hDisMatters;
        }

        public void setHDisMatters(String hDisMatters) {
            this.hDisMatters = hDisMatters;
        }

        public String getHPreMan() {
            return hPreMan;
        }

        public void setHPreMan(String hPreMan) {
            this.hPreMan = hPreMan;
        }

        public String getHPrePhone() {
            return hPrePhone;
        }

        public void setHPrePhone(String hPrePhone) {
            this.hPrePhone = hPrePhone;
        }

        public String getHSignalMan() {
            return hSignalMan;
        }

        public void setHSignalMan(String hSignalMan) {
            this.hSignalMan = hSignalMan;
        }

        public String getHSignalPhone() {
            return hSignalPhone;
        }

        public void setHSignalPhone(String hSignalPhone) {
            this.hSignalPhone = hSignalPhone;
        }

        public String getHEvaLine() {
            return hEvaLine;
        }

        public void setHEvaLine(String hEvaLine) {
            this.hEvaLine = hEvaLine;
        }

        public String getHPlacementMan() {
            return hPlacementMan;
        }

        public void setHPlacementMan(String hPlacementMan) {
            this.hPlacementMan = hPlacementMan;
        }

        public String getHPlacementPhone() {
            return hPlacementPhone;
        }

        public void setHPlacementPhone(String hPlacementPhone) {
            this.hPlacementPhone = hPlacementPhone;
        }

        public String getHAmbulanceMan() {
            return hAmbulanceMan;
        }

        public void setHAmbulanceMan(String hAmbulanceMan) {
            this.hAmbulanceMan = hAmbulanceMan;
        }

        public String getHAmbulancePhone() {
            return hAmbulancePhone;
        }

        public void setHAmbulancePhone(String hAmbulancePhone) {
            this.hAmbulancePhone = hAmbulancePhone;
        }

        public String getHGrantPhone() {
            return hGrantPhone;
        }

        public void setHGrantPhone(String hGrantPhone) {
            this.hGrantPhone = hGrantPhone;
        }
    }
}
