package com.nandi.disastermanager.entity;

import java.util.List;

/**
 * Created by lemon on 2017/8/12.
 */

public class SearchPerson {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"lon":106.8083,"phone":"15702327880","area_parent":275,"name":"张先容","type_name":"群测群防人","lat":28.7716},{"lon":106.9236,"phone":"15702327870","area_parent":275,"name":"张友春","type_name":"群测群防人","lat":29.0141},{"lon":106.9272,"phone":"15702327991","area_parent":275,"name":"张后吉","type_name":"群测群防人","lat":29.0336},{"lon":106.8872,"phone":"15702327955","area_parent":275,"name":"张大会","type_name":"群测群防人","lat":29.0544},{"lon":106.9158,"phone":"15702327842","area_parent":275,"name":"张方国","type_name":"群测群防人","lat":28.9158},{"lon":106.9452,"phone":"15702327923","area_parent":275,"name":"张祥林","type_name":"群测群防人","lat":28.9719},{"lon":106.8697,"phone":"15702327855","area_parent":275,"name":"张绍书","type_name":"群测群防人","lat":28.8233},{"lon":106.8102,"phone":"15702327853","area_parent":275,"name":"张绍全","type_name":"群测群防人","lat":28.8488},{"lon":106.873,"phone":"15702327850","area_parent":275,"name":"张绍宽","type_name":"群测群防人","lat":28.8202},{"lon":106.9255,"phone":"15702327914","area_parent":275,"name":"张维约","type_name":"群测群防人","lat":28.9969},{"lon":106.8675,"phone":"15702327961","area_parent":275,"name":"张锡利","type_name":"群测群防人","lat":29.0994}]
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
         * lon : 106.8083
         * phone : 15702327880
         * area_parent : 275
         * name : 张先容
         * type_name : 群测群防人
         * lat : 28.7716
         */

        private double lon;
        private String phone;
        private int area_parent;
        private String name;
        private String type_name;
        private double lat;

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getArea_parent() {
            return area_parent;
        }

        public void setArea_parent(int area_parent) {
            this.area_parent = area_parent;
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

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
