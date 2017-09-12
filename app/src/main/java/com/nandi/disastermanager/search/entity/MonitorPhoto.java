package com.nandi.disastermanager.search.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingsong on 2017/9/11.
 */

public class MonitorPhoto implements Serializable{


    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"name":"卧牛村不稳定斜坡_01_20170810104227.jpg","url":"D:/GeoDisasterPhoto/卧牛村不稳定斜坡_01_20170810104227.jpg"},{"name":"卧牛村不稳定斜坡_01_20170810104014.jpg","url":"D:/GeoDisasterPhoto/卧牛村不稳定斜坡_01_20170810104014.jpg"},{"name":"拥党村六组不稳定斜坡（滑坡）_01_20170808103859.jpg","url":"D:/GeoDisasterPhoto/拥党村六组不稳定斜坡（滑坡）_01_20170808103859.jpg"}]
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

    public static class MetaBean implements Serializable {
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

    public static class DataBean implements Serializable{
        /**
         * name : 卧牛村不稳定斜坡_01_20170810104227.jpg
         * url : D:/GeoDisasterPhoto/卧牛村不稳定斜坡_01_20170810104227.jpg
         */

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
