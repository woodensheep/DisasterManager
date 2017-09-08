package com.nandi.disastermanager.search.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lemon on 2017/9/7.
 */

public class DisasterInfo {


    /**
     * meta : {"success":"true","message":"ok"}
     * data : {"0":{"site":"贵阳市贵阳市乌当区百花湖乡哪嘎","threatMoney":"32.00","threatObject":"村镇,","lon":"106.5227778 ","auditor":"周维星","disNum":"520112000002","threatFamily":"8","inducement":"降雨,动水压力,坡脚冲刷,坡脚浸润,","areacode":"520112","villages":"百花湖乡","type":"dis06b","preparer":"张涛","threatNum":"40","unit":"贵州正业工程技术投资有限公司","fillingData":"2012-07-12","name":"小登坡不稳定斜坡","type_name":"不稳定滑坡","opinion":",工程治理,工程治理,工程治理,工程治理","threatLevel":"小型","lat":"26.7422222 "}}
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

        private String success;
        private String message;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
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
         * 0 : {"site":"贵阳市贵阳市乌当区百花湖乡哪嘎","threatMoney":"32.00","threatObject":"村镇,","lon":"106.5227778 ","auditor":"周维星","disNum":"520112000002","threatFamily":"8","inducement":"降雨,动水压力,坡脚冲刷,坡脚浸润,","areacode":"520112","villages":"百花湖乡","type":"dis06b","preparer":"张涛","threatNum":"40","unit":"贵州正业工程技术投资有限公司","fillingData":"2012-07-12","name":"小登坡不稳定斜坡","type_name":"不稳定滑坡","opinion":",工程治理,工程治理,工程治理,工程治理","threatLevel":"小型","lat":"26.7422222 "}
         */

        @SerializedName("0")
        private _$0Bean _$0;

        public _$0Bean get_$0() {
            return _$0;
        }

        public void set_$0(_$0Bean _$0) {
            this._$0 = _$0;
        }

        public static class _$0Bean {
            /**
             * site : 贵阳市贵阳市乌当区百花湖乡哪嘎
             * threatMoney : 32.00
             * threatObject : 村镇,
             * lon : 106.5227778
             * auditor : 周维星
             * disNum : 520112000002
             * threatFamily : 8
             * inducement : 降雨,动水压力,坡脚冲刷,坡脚浸润,
             * areacode : 520112
             * villages : 百花湖乡
             * type : dis06b
             * preparer : 张涛
             * threatNum : 40
             * unit : 贵州正业工程技术投资有限公司
             * fillingData : 2012-07-12
             * name : 小登坡不稳定斜坡
             * type_name : 不稳定滑坡
             * opinion : ,工程治理,工程治理,工程治理,工程治理
             * threatLevel : 小型
             * lat : 26.7422222
             */

            private String site;
            private String threatMoney;
            private String threatObject;
            private String lon;
            private String auditor;
            private String disNum;
            private String threatFamily;
            private String inducement;
            private String areacode;
            private String villages;
            private String type;
            private String preparer;
            private String threatNum;
            private String unit;
            private String fillingData;
            private String name;
            private String type_name;
            private String opinion;
            private String threatLevel;
            private String lat;

            public String getSite() {
                return site;
            }

            public void setSite(String site) {
                this.site = site;
            }

            public String getThreatMoney() {
                return threatMoney;
            }

            public void setThreatMoney(String threatMoney) {
                this.threatMoney = threatMoney;
            }

            public String getThreatObject() {
                return threatObject;
            }

            public void setThreatObject(String threatObject) {
                this.threatObject = threatObject;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
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

            public String getInducement() {
                return inducement;
            }

            public void setInducement(String inducement) {
                this.inducement = inducement;
            }

            public String getAreacode() {
                return areacode;
            }

            public void setAreacode(String areacode) {
                this.areacode = areacode;
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

            public String getThreatNum() {
                return threatNum;
            }

            public void setThreatNum(String threatNum) {
                this.threatNum = threatNum;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getFillingData() {
                return fillingData;
            }

            public void setFillingData(String fillingData) {
                this.fillingData = fillingData;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getOpinion() {
                return opinion;
            }

            public void setOpinion(String opinion) {
                this.opinion = opinion;
            }

            public String getThreatLevel() {
                return threatLevel;
            }

            public void setThreatLevel(String threatLevel) {
                this.threatLevel = threatLevel;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }
        }
    }
}
