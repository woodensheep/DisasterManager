package com.nandi.disastermanager.entity;

/**
 * Created by lemon on 2017/8/10.
 * 防灾明白卡
 */

public class DetailDisCard {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"id":11222,"dMonitorMan":"朱绍碧","dMonitorPhone":"18736673079","dEPlace":"毛里村办公室","dELine":"往马颈子方向","dESignal":"铜锣","dEvacuateMan":"朱绍碧","dEvacuatePhone":"18736673079","dExcludeMan":"镇村社","dExcludePhone":"48351067","dSecurityMan":"派出所","dSecurityPhone":"65968425","dDocMan":"卫生院","dDocPhone":"48351051","dGrantDate":"2016-3-20","dHoldUnit":"朱绍碧","dHoldPhone":"18736673079","dHoldDate":"2016-3-20","dNo":"5001101020010101","dPosition":"万盛区  青年 乡（镇） 毛里村关山沟社","dType":"滑坡 长：70m 宽：60m  高：3m体积:1.26万M3","dInduceFactor":"大暴雨","dThreat":"罗聪等2户10人","dMonitorSign":"裂缝变化","dAlarmType":"鸣锣 呼喊","dMonitorJudge":"观测裂缝加剧、变宽的变化情况","dGrantUnit":"青年镇人民政府","dGrantPhone":"48351067"}
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
         * id : 11222
         * dMonitorMan : 朱绍碧
         * dMonitorPhone : 18736673079
         * dEPlace : 毛里村办公室
         * dELine : 往马颈子方向
         * dESignal : 铜锣
         * dEvacuateMan : 朱绍碧
         * dEvacuatePhone : 18736673079
         * dExcludeMan : 镇村社
         * dExcludePhone : 48351067
         * dSecurityMan : 派出所
         * dSecurityPhone : 65968425
         * dDocMan : 卫生院
         * dDocPhone : 48351051
         * dGrantDate : 2016-3-20
         * dHoldUnit : 朱绍碧
         * dHoldPhone : 18736673079
         * dHoldDate : 2016-3-20
         * dNo : 5001101020010101
         * dPosition : 万盛区  青年 乡（镇） 毛里村关山沟社
         * dType : 滑坡 长：70m 宽：60m  高：3m体积:1.26万M3
         * dInduceFactor : 大暴雨
         * dThreat : 罗聪等2户10人
         * dMonitorSign : 裂缝变化
         * dAlarmType : 鸣锣 呼喊
         * dMonitorJudge : 观测裂缝加剧、变宽的变化情况
         * dGrantUnit : 青年镇人民政府
         * dGrantPhone : 48351067
         */

        private int id;
        private String dMonitorMan;
        private String dMonitorPhone;
        private String dEPlace;
        private String dELine;
        private String dESignal;
        private String dEvacuateMan;
        private String dEvacuatePhone;
        private String dExcludeMan;
        private String dExcludePhone;
        private String dSecurityMan;
        private String dSecurityPhone;
        private String dDocMan;
        private String dDocPhone;
        private String dGrantDate;
        private String dHoldUnit;
        private String dHoldPhone;
        private String dHoldDate;
        private String dNo;
        private String dPosition;
        private String dType;
        private String dInduceFactor;
        private String dThreat;
        private String dMonitorSign;
        private String dAlarmType;
        private String dMonitorJudge;
        private String dGrantUnit;
        private String dGrantPhone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDMonitorMan() {
            return dMonitorMan;
        }

        public void setDMonitorMan(String dMonitorMan) {
            this.dMonitorMan = dMonitorMan;
        }

        public String getDMonitorPhone() {
            return dMonitorPhone;
        }

        public void setDMonitorPhone(String dMonitorPhone) {
            this.dMonitorPhone = dMonitorPhone;
        }

        public String getDEPlace() {
            return dEPlace;
        }

        public void setDEPlace(String dEPlace) {
            this.dEPlace = dEPlace;
        }

        public String getDELine() {
            return dELine;
        }

        public void setDELine(String dELine) {
            this.dELine = dELine;
        }

        public String getDESignal() {
            return dESignal;
        }

        public void setDESignal(String dESignal) {
            this.dESignal = dESignal;
        }

        public String getDEvacuateMan() {
            return dEvacuateMan;
        }

        public void setDEvacuateMan(String dEvacuateMan) {
            this.dEvacuateMan = dEvacuateMan;
        }

        public String getDEvacuatePhone() {
            return dEvacuatePhone;
        }

        public void setDEvacuatePhone(String dEvacuatePhone) {
            this.dEvacuatePhone = dEvacuatePhone;
        }

        public String getDExcludeMan() {
            return dExcludeMan;
        }

        public void setDExcludeMan(String dExcludeMan) {
            this.dExcludeMan = dExcludeMan;
        }

        public String getDExcludePhone() {
            return dExcludePhone;
        }

        public void setDExcludePhone(String dExcludePhone) {
            this.dExcludePhone = dExcludePhone;
        }

        public String getDSecurityMan() {
            return dSecurityMan;
        }

        public void setDSecurityMan(String dSecurityMan) {
            this.dSecurityMan = dSecurityMan;
        }

        public String getDSecurityPhone() {
            return dSecurityPhone;
        }

        public void setDSecurityPhone(String dSecurityPhone) {
            this.dSecurityPhone = dSecurityPhone;
        }

        public String getDDocMan() {
            return dDocMan;
        }

        public void setDDocMan(String dDocMan) {
            this.dDocMan = dDocMan;
        }

        public String getDDocPhone() {
            return dDocPhone;
        }

        public void setDDocPhone(String dDocPhone) {
            this.dDocPhone = dDocPhone;
        }

        public String getDGrantDate() {
            return dGrantDate;
        }

        public void setDGrantDate(String dGrantDate) {
            this.dGrantDate = dGrantDate;
        }

        public String getDHoldUnit() {
            return dHoldUnit;
        }

        public void setDHoldUnit(String dHoldUnit) {
            this.dHoldUnit = dHoldUnit;
        }

        public String getDHoldPhone() {
            return dHoldPhone;
        }

        public void setDHoldPhone(String dHoldPhone) {
            this.dHoldPhone = dHoldPhone;
        }

        public String getDHoldDate() {
            return dHoldDate;
        }

        public void setDHoldDate(String dHoldDate) {
            this.dHoldDate = dHoldDate;
        }

        public String getDNo() {
            return dNo;
        }

        public void setDNo(String dNo) {
            this.dNo = dNo;
        }

        public String getDPosition() {
            return dPosition;
        }

        public void setDPosition(String dPosition) {
            this.dPosition = dPosition;
        }

        public String getDType() {
            return dType;
        }

        public void setDType(String dType) {
            this.dType = dType;
        }

        public String getDInduceFactor() {
            return dInduceFactor;
        }

        public void setDInduceFactor(String dInduceFactor) {
            this.dInduceFactor = dInduceFactor;
        }

        public String getDThreat() {
            return dThreat;
        }

        public void setDThreat(String dThreat) {
            this.dThreat = dThreat;
        }

        public String getDMonitorSign() {
            return dMonitorSign;
        }

        public void setDMonitorSign(String dMonitorSign) {
            this.dMonitorSign = dMonitorSign;
        }

        public String getDAlarmType() {
            return dAlarmType;
        }

        public void setDAlarmType(String dAlarmType) {
            this.dAlarmType = dAlarmType;
        }

        public String getDMonitorJudge() {
            return dMonitorJudge;
        }

        public void setDMonitorJudge(String dMonitorJudge) {
            this.dMonitorJudge = dMonitorJudge;
        }

        public String getDGrantUnit() {
            return dGrantUnit;
        }

        public void setDGrantUnit(String dGrantUnit) {
            this.dGrantUnit = dGrantUnit;
        }

        public String getDGrantPhone() {
            return dGrantPhone;
        }

        public void setDGrantPhone(String dGrantPhone) {
            this.dGrantPhone = dGrantPhone;
        }
    }
}
