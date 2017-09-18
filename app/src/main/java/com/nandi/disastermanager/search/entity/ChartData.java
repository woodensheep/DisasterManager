package com.nandi.disastermanager.search.entity;

import java.util.List;

/**
 * Created by lemon on 2017/9/11.
 */

public class ChartData {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : {"MONITORDATA":[[1502097570000,121],[1502097661000,121],[1502158712000,121],[1502158999000,121],[1502159953000,10],[1502332821000,1],[1502332979000,1],[1503017949000,121],[1503018169000,121]]}
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
        private List<List<Double>> MONITORDATA;

        public List<List<Double>> getMONITORDATA() {
            return MONITORDATA;
        }

        public void setMONITORDATA(List<List<Double>> MONITORDATA) {
            this.MONITORDATA = MONITORDATA;
        }
    }
}
