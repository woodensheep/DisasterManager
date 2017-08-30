package com.nandi.disastermanager.entity;

/**
 * Created by lemon on 2017/8/22.
 * 单个设备信息
 */

public class SingleEquipment {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"device_no":"0000F3","device_name":"0000F3","hardware_version":"0000F3","soft_version":"0000F3","table_name":"gps$gysfa22","static_name":"三项测缝计","monitor_no":"0000F3","monitor_name":"庆口危岩4号","monitor_type":23,"register_date":"2015-08-28T00:00:00","state":1,"project_name":"羊角场镇危岩专业预警监测-水环中心","project_add":"","project_man":"","start_date":"2015-08-21T00:00:00","latitude":29.375,"longitude":107.58056}
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
         * device_no : 0000F3
         * device_name : 0000F3
         * hardware_version : 0000F3
         * soft_version : 0000F3
         * table_name : gps$gysfa22
         * static_name : 三项测缝计
         * monitor_no : 0000F3
         * monitor_name : 庆口危岩4号
         * monitor_type : 23
         * register_date : 2015-08-28T00:00:00
         * state : 1
         * project_name : 羊角场镇危岩专业预警监测-水环中心
         * project_add :
         * project_man :
         * start_date : 2015-08-21T00:00:00
         * latitude : 29.375
         * longitude : 107.58056
         */

        private String device_no;
        private String device_name;
        private String hardware_version;
        private String soft_version;
        private String table_name;
        private String static_name;
        private String monitor_no;
        private String monitor_name;
        private int monitor_type;
        private String register_date;
        private int state;
        private String project_name;
        private String project_add;
        private String project_man;
        private String start_date;
        private double latitude;
        private double longitude;

        public String getDevice_no() {
            return device_no;
        }

        public void setDevice_no(String device_no) {
            this.device_no = device_no;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public String getHardware_version() {
            return hardware_version;
        }

        public void setHardware_version(String hardware_version) {
            this.hardware_version = hardware_version;
        }

        public String getSoft_version() {
            return soft_version;
        }

        public void setSoft_version(String soft_version) {
            this.soft_version = soft_version;
        }

        public String getTable_name() {
            return table_name;
        }

        public void setTable_name(String table_name) {
            this.table_name = table_name;
        }

        public String getStatic_name() {
            return static_name;
        }

        public void setStatic_name(String static_name) {
            this.static_name = static_name;
        }

        public String getMonitor_no() {
            return monitor_no;
        }

        public void setMonitor_no(String monitor_no) {
            this.monitor_no = monitor_no;
        }

        public String getMonitor_name() {
            return monitor_name;
        }

        public void setMonitor_name(String monitor_name) {
            this.monitor_name = monitor_name;
        }

        public int getMonitor_type() {
            return monitor_type;
        }

        public void setMonitor_type(int monitor_type) {
            this.monitor_type = monitor_type;
        }

        public String getRegister_date() {
            return register_date;
        }

        public void setRegister_date(String register_date) {
            this.register_date = register_date;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getProject_add() {
            return project_add;
        }

        public void setProject_add(String project_add) {
            this.project_add = project_add;
        }

        public String getProject_man() {
            return project_man;
        }

        public void setProject_man(String project_man) {
            this.project_man = project_man;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
