package com.nandi.disastermanager.search.entity;

import java.util.List;

/**
 * Created by qingsong on 2017/9/8.
 */

public class MonitorData {


    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"time":"2017-09-10 15:51:54","monitorId":"520402010003_01","name":"轿子山镇厦耳村前寨滑坡_01","monitorData":1852},{"time":"2017-09-10 18:41:16","monitorId":"520425010021_01","name":"湖坝冲滑坡_01","monitorData":200},{"time":"2017-09-18 13:24:28","monitorId":"520402010003_01","name":"轿子山镇厦耳村前寨滑坡_01","monitorData":250},{"time":"2017-09-11 12:36:29","monitorId":"520402050003_01","name":"老林寨地裂缝_01","monitorData":55},{"time":"2017-09-10 12:36:29","monitorId":"520402050003_02","name":"老林寨地裂缝_02","monitorData":50},{"time":"2017-09-08 12:36:29","monitorId":"520402050003_01","name":"老林寨地裂缝_01","monitorData":57},{"time":"2017-09-02 12:36:29","monitorId":"520402050003_01","name":"老林寨地裂缝_01","monitorData":58},{"time":"2017-09-06 12:36:29","monitorId":"520402050003_02","name":"老林寨地裂缝_02","monitorData":48},{"time":"2017-09-01 12:36:29","monitorId":"520402050003_02","name":"老林寨地裂缝_02","monitorData":56},{"time":"2017-09-12 11:36:29","monitorId":"520402050003_01","name":"老林寨地裂缝_01","monitorData":55},{"time":"2017-09-12 12:36:29","monitorId":"520402050003_01","name":"老林寨地裂缝_01","monitorData":55},{"time":"2017-09-12 14:36:29","monitorId":"520402050003_01","name":"老林寨地裂缝_01","monitorData":55},{"time":"2017-09-13 15:23:02","monitorId":"520403070043_01","name":"胡家岩崩塌（危岩体）_01","monitorData":35}]
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
         * time : 2017-09-10 15:51:54
         * monitorId : 520402010003_01
         * name : 轿子山镇厦耳村前寨滑坡_01
         * monitorData : 1852.0
         */

        private String time;
        private String monitorId;
        private String name;
        private double monitorData;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMonitorId() {
            return monitorId;
        }

        public void setMonitorId(String monitorId) {
            this.monitorId = monitorId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getMonitorData() {
            return monitorData;
        }

        public void setMonitorData(double monitorData) {
            this.monitorData = monitorData;
        }
    }
}
