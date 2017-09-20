package com.nandi.disastermanager.search.entity;

import java.util.List;

/**
 * Created by qingsong on 2017/9/9.
 */

public class MonitorListData {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"NAME":"轿子山镇厦耳村前寨滑坡_01","ID":"520402010003_01","LONGITUDE":106.689,"HTPID":"520402010003","LATITUDE":26.591171,"REGIONID":2151},{"NAME":"老林寨地裂缝_01","ID":"520402050003_01","LONGITUDE":106.0425,"HTPID":"520402050003","LATITUDE":26.388889,"REGIONID":2180},{"NAME":"老林寨地裂缝_02","ID":"520402050003_02","LONGITUDE":106.0425,"HTPID":"520402050003","LATITUDE":26.388889,"REGIONID":2180},{"NAME":"胡家岩崩塌（危岩体）_01","ID":"520403070043_01","LONGITUDE":106.388889,"HTPID":"520403070043","LATITUDE":26.361667,"REGIONID":2193},{"NAME":"湖坝冲滑坡_01","ID":"520425010021_01","LONGITUDE":106.697646,"HTPID":"520425010021","LATITUDE":26.59777,"REGIONID":2620}]
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
         * NAME : 轿子山镇厦耳村前寨滑坡_01
         * ID : 520402010003_01
         * LONGITUDE : 106.689
         * HTPID : 520402010003
         * LATITUDE : 26.591171
         * REGIONID : 2151
         */

        private String NAME;
        private String ID;
        private double LONGITUDE;
        private String HTPID;
        private double LATITUDE;
        private int REGIONID;

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public double getLONGITUDE() {
            return LONGITUDE;
        }

        public void setLONGITUDE(double LONGITUDE) {
            this.LONGITUDE = LONGITUDE;
        }

        public String getHTPID() {
            return HTPID;
        }

        public void setHTPID(String HTPID) {
            this.HTPID = HTPID;
        }

        public double getLATITUDE() {
            return LATITUDE;
        }

        public void setLATITUDE(double LATITUDE) {
            this.LATITUDE = LATITUDE;
        }

        public int getREGIONID() {
            return REGIONID;
        }

        public void setREGIONID(int REGIONID) {
            this.REGIONID = REGIONID;
        }
    }
}
