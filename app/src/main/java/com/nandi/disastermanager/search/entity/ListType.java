package com.nandi.disastermanager.search.entity;

import java.util.List;

/**
 * Created by lemon on 2017/9/11.
 * type类型
 */

public class ListType {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"yfys":["人为","自然"],"xqdj":["中型","小型"]}
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
        private List<String> yfys;
        private List<String> xqdj;

        public List<String> getYfys() {
            return yfys;
        }

        public void setYfys(List<String> yfys) {
            this.yfys = yfys;
        }

        public List<String> getXqdj() {
            return xqdj;
        }

        public void setXqdj(List<String> xqdj) {
            this.xqdj = xqdj;
        }
    }
}
