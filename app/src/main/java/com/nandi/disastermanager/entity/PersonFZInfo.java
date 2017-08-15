package com.nandi.disastermanager.entity;

import java.util.List;

/**
 * Created by lemon on 2017/8/14.
 */

public class PersonFZInfo {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"area_location":"重庆市綦江区三角镇","id":89,"area_id":860,"lon":106.747834,"real_mobile":"15334565628","area_name":"三角镇","admin_pic":"qijiang/wangshoujin.jpg","admin_name":"王守进","telephone":"15702327599","lat":29.052342}]
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
         * area_location : 重庆市綦江区三角镇
         * id : 89
         * area_id : 860
         * lon : 106.747834
         * real_mobile : 15334565628
         * area_name : 三角镇
         * admin_pic : qijiang/wangshoujin.jpg
         * admin_name : 王守进
         * telephone : 15702327599
         * lat : 29.052342
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
        private String onlineStatus;
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

        public String getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(String onlineStatus) {
            this.onlineStatus = onlineStatus;
        }
    }
}
