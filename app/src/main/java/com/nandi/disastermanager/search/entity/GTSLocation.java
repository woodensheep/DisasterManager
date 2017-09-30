package com.nandi.disastermanager.search.entity;

import java.util.List;

/**
 * Created by qingsong on 2017/9/29.
 */

public class GTSLocation {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"jd":105.51676,"adminname":"织金县","town":"阿弓镇","wd":26.47976}]
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
         * jd : 105.51676
         * adminname : 织金县
         * town : 阿弓镇
         * wd : 26.47976
         */

        private double jd;
        private String adminname;
        private String town;
        private double wd;

        public double getJd() {
            return jd;
        }

        public void setJd(double jd) {
            this.jd = jd;
        }

        public String getAdminname() {
            return adminname;
        }

        public void setAdminname(String adminname) {
            this.adminname = adminname;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public double getWd() {
            return wd;
        }

        public void setWd(double wd) {
            this.wd = wd;
        }
    }
}
