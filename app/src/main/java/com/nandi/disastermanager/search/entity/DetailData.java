package com.nandi.disastermanager.search.entity;

import java.util.List;

/**
 * Created by qingsong on 2017/9/8.
 */

public class DetailData {


    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"site":"安顺市西秀区蔡官镇嘎哩","scale":"1300","threatMoney":"1000","lon":105.974167,"fzzrr":"张文学","auditor":"刘刚","disNum":"520402010001","threatFamily":"105","fxsj":"2015.10.30","areacode":520402,"inducement":"人为","villages":"嘎哩","type":"01","preparer":"试试","sbsj":"2015.10.30","threatNum":"410","fzzrdw":"蔡官镇政府","fzzrrdh":"13885308166","wxqt":"村民及房屋","name":"嘎哩滑坡","threatLevel":"中型","zlqk":"监测；预警；避让","lat":26.389444}]
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
         * site : 安顺市西秀区蔡官镇嘎哩
         * scale : 1300
         * threatMoney : 1000
         * lon : 105.974167
         * fzzrr : 张文学
         * auditor : 刘刚
         * disNum : 520402010001
         * threatFamily : 105
         * fxsj : 2015.10.30
         * areacode : 520402
         * inducement : 人为
         * villages : 嘎哩
         * type : 01
         * preparer : 试试
         * sbsj : 2015.10.30
         * threatNum : 410
         * fzzrdw : 蔡官镇政府
         * fzzrrdh : 13885308166
         * wxqt : 村民及房屋
         * name : 嘎哩滑坡
         * threatLevel : 中型
         * zlqk : 监测；预警；避让
         * lat : 26.389444
         */

        private String site;
        private String scale;
        private String threatMoney;
        private double lon;
        private String fzzrr;
        private String auditor;
        private String disNum;
        private String threatFamily;
        private String fxsj;
        private int areacode;
        private String inducement;
        private String villages;
        private String type;
        private String preparer;
        private String sbsj;
        private String threatNum;
        private String fzzrdw;
        private String fzzrrdh;
        private String wxqt;
        private String name;
        private String threatLevel;
        private String zlqk;
        private double lat;
        private String GATHER;
        private String PHONE;
        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getScale() {
            return scale;
        }

        public void setScale(String scale) {
            this.scale = scale;
        }

        public String getThreatMoney() {
            return threatMoney;
        }

        public void setThreatMoney(String threatMoney) {
            this.threatMoney = threatMoney;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getFzzrr() {
            return fzzrr;
        }

        public void setFzzrr(String fzzrr) {
            this.fzzrr = fzzrr;
        }

        public String getAuditor() {
            return auditor;
        }

        public void setAuditor(String auditor) {
            this.auditor = auditor;
        }

        public String getDisNum() {
            return disNum;
        }

        public void setDisNum(String disNum) {
            this.disNum = disNum;
        }

        public String getThreatFamily() {
            return threatFamily;
        }

        public void setThreatFamily(String threatFamily) {
            this.threatFamily = threatFamily;
        }

        public String getFxsj() {
            return fxsj;
        }

        public void setFxsj(String fxsj) {
            this.fxsj = fxsj;
        }

        public int getAreacode() {
            return areacode;
        }

        public void setAreacode(int areacode) {
            this.areacode = areacode;
        }

        public String getInducement() {
            return inducement;
        }

        public void setInducement(String inducement) {
            this.inducement = inducement;
        }

        public String getVillages() {
            return villages;
        }

        public void setVillages(String villages) {
            this.villages = villages;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPreparer() {
            return preparer;
        }

        public void setPreparer(String preparer) {
            this.preparer = preparer;
        }

        public String getSbsj() {
            return sbsj;
        }

        public void setSbsj(String sbsj) {
            this.sbsj = sbsj;
        }

        public String getThreatNum() {
            return threatNum;
        }

        public void setThreatNum(String threatNum) {
            this.threatNum = threatNum;
        }

        public String getFzzrdw() {
            return fzzrdw;
        }

        public void setFzzrdw(String fzzrdw) {
            this.fzzrdw = fzzrdw;
        }

        public String getFzzrrdh() {
            return fzzrrdh;
        }

        public void setFzzrrdh(String fzzrrdh) {
            this.fzzrrdh = fzzrrdh;
        }

        public String getWxqt() {
            return wxqt;
        }

        public void setWxqt(String wxqt) {
            this.wxqt = wxqt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThreatLevel() {
            return threatLevel;
        }

        public void setThreatLevel(String threatLevel) {
            this.threatLevel = threatLevel;
        }

        public String getZlqk() {
            return zlqk;
        }

        public void setZlqk(String zlqk) {
            this.zlqk = zlqk;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public String getGATHER() {
            return GATHER;
        }

        public void setGATHER(String GATHER) {
            this.GATHER = GATHER;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }
    }
}
